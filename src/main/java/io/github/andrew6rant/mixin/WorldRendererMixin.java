package io.github.andrew6rant.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.andrew6rant.VariableWeatherClient;
import io.github.andrew6rant.weather.WeatherManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.ParticlesMode;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.FluidState;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Final @Shadow @Mutable
    private final MinecraftClient client;

    @Shadow private int ticks;

    @Final @Shadow @Mutable private final float[] field_20794;
    @Final @Shadow @Mutable private final float[] field_20795;

    @Shadow private int rainSoundCounter;

    @Final @Shadow @Mutable private static final Identifier RAIN = new Identifier("textures/environment/rain.png");
    @Final @Shadow @Mutable private static final Identifier SNOW = new Identifier("textures/environment/snow.png");

    @Shadow private ClientWorld world;

    private static final Identifier LIGHT_RAIN = new Identifier("variable-weather:textures/environment/light_rain.png");
    private static final Identifier MEDIUM_RAIN = new Identifier("variable-weather:textures/environment/medium_light_rain.png");

    private static final Identifier HEAVY_RAIN = new Identifier("variable-weather:textures/environment/heavy_rain.png");

    private static final Identifier LIGHT_SNOW = new Identifier("variable-weather:textures/environment/light_snow.png");

    private static final Identifier MEDIUM_SNOW = new Identifier("variable-weather:textures/environment/medium_snow.png");

    private static final Identifier HEAVY_SNOW = new Identifier("variable-weather:textures/environment/heavy_snow.png");

    private static final Identifier LIGHT_SAND = new Identifier("variable-weather:textures/environment/light_sand.png");

    private static final Identifier HEAVY_SAND = new Identifier("variable-weather:textures/environment/heavy_sand.png");

    private static final Identifier DEBUG = new Identifier("variable-weather:textures/environment/debug.png");

    private float angleX = 0;
    private float angleZ = 0;

    public WorldRendererMixin(MinecraftClient client, int ticks, float[] field20794, float[] field20795) {
        this.client = client;
        this.ticks = ticks;
        field_20794 = field20794;
        field_20795 = field20795;
    }

    @Inject(method = "Lnet/minecraft/client/render/WorldRenderer;render(Lnet/minecraft/client/util/math/MatrixStack;FJZLnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/GameRenderer;Lnet/minecraft/client/render/LightmapTextureManager;Lorg/joml/Matrix4f;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;renderLayer(Lnet/minecraft/client/render/RenderLayer;Lnet/minecraft/client/util/math/MatrixStack;DDDLorg/joml/Matrix4f;)V", ordinal = 2, shift = At.Shift.AFTER))
    private void render(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f positionMatrix, CallbackInfo ci) {
        int size = 10;
        if (VariableWeatherClient.renderWindVisualization()) {
            for (int x = -size; x < size; x++) {
                for (int z = -size; z < size; z++) {
                    Vec2f wind = WeatherManager.getWindPolar(world, new Vec3d(x + Math.floor(client.gameRenderer.getCamera().getPos().getX()), client.gameRenderer.getCamera().getPos().getY(), z + Math.floor(client.gameRenderer.getCamera().getPos().getZ())), 1.0F);

                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();
                    RenderSystem.depthMask(false);
                    RenderSystem.enableDepthTest();
                    RenderSystem.polygonOffset(-3.0F, -3.0F);
                    RenderSystem.enablePolygonOffset();
                    RenderSystem.setShader(GameRenderer::getPositionColorProgram);
                    Tessellator tessellator = Tessellator.getInstance();
                    BufferBuilder bufferBuilder = tessellator.getBuffer();

                    matrices.push();

                    matrices.translate(-client.gameRenderer.getCamera().getPos().getX(), -client.gameRenderer.getCamera().getPos().getY(), -client.gameRenderer.getCamera().getPos().getZ());
                    matrices.translate(Math.floor(client.gameRenderer.getCamera().getPos().getX()), Math.floor(client.gameRenderer.getCamera().getPos().getY()), Math.floor(client.gameRenderer.getCamera().getPos().getZ()));
                    matrices.translate(x, 0, z);

                    matrices.translate(0.5D, 0, 0.5D);
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(wind.y));
                    matrices.translate(-0.5D, 0, -0.5D);

                    Matrix4f matrix4f = matrices.peek().getPositionMatrix();
                    bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);

                    float thickness = 8.0F;

                    float col = wind.x;
                    bufferBuilder.vertex(matrix4f, (8.0F + (thickness / 2.0F)) / 16.0F, -1.0F, 0.0F / 16.0F).color(col, col, col, 0.3F).next();
                    bufferBuilder.vertex(matrix4f, (8.0F - (thickness / 2.0F)) / 16.0F, -1.0F, 0.0F / 16.0F).color(col, col, col, 0.3F).next();
                    bufferBuilder.vertex(matrix4f, 8.0F / 16.0F, -1.0F, 1.0f).color(col, col, col, 0.3F).next();
                    bufferBuilder.vertex(matrix4f, 8.0F / 16.0F, -1.0F, 1.0f).color(col, col, col, 0.3F).next();
                    tessellator.draw();

                    matrices.pop();

                    RenderSystem.polygonOffset(0.0F, 0.0F);
                    RenderSystem.disablePolygonOffset();
                    RenderSystem.depthMask(true);
                    RenderSystem.disableDepthTest();
                    RenderSystem.disableBlend();
                }
            }
        }
    }


    /**
     * @author Andrew6rant (Andrew Grant)
     * @reason Make a big overwrite for ease of use, I am later going to refine it into multiple injects for compatibility
     */
    @Overwrite
    public void renderWeather(LightmapTextureManager manager, float tickDelta, double cameraX, double cameraY, double cameraZ) {
        float rainGradient = this.client.world.getRainGradient(tickDelta);
        if (rainGradient == 0.0F) {
            angleX = MathHelper.clamp(angleX, -15F, 15F);
            angleZ = MathHelper.clamp(angleX, -15F, 15F);
        }
        if (!(rainGradient <= 0.0F)) {
            Random randcheck = Random.create(System.currentTimeMillis());
            manager.enable();
            World world = this.client.world;
            boolean thundering = world.isThundering();
            MatrixStack matrixStack = new MatrixStack();
            Matrix4f positionMatrix = matrixStack.peek().getPositionMatrix();

            if (thundering) {
                angleX = (float) MathHelper.clamp(angleX + (randcheck.nextGaussian() / 3F), -35F, 35F);
                angleZ = (float) MathHelper.clamp(angleX + (randcheck.nextGaussian() / 3F), -35F, 35F);
            } else {
                angleX = (float) MathHelper.clamp(angleX + (randcheck.nextGaussian() / 10F), -20F, 20F);
                angleZ = (float) MathHelper.clamp(angleX + (randcheck.nextGaussian() / 10F), -20F, 20F);
            }

            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(angleX));
            matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(angleZ));

            int playerPosX = MathHelper.floor(cameraX);
            int playerPosY = MathHelper.floor(cameraY);
            int playerPosZ = MathHelper.floor(cameraZ);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder = tessellator.getBuffer();
            RenderSystem.disableCull();
            RenderSystem.enableBlend();
            RenderSystem.enableDepthTest();
            int layers = 5;
            if (MinecraftClient.isFancyGraphicsOrBetter()) {
                layers = 10;
            }

            RenderSystem.depthMask(MinecraftClient.isFabulousGraphicsOrBetter());
            int m = -1;
            float smoothTicks = (float)this.ticks + tickDelta;
            RenderSystem.setShader(GameRenderer::getParticleProgram);
            BlockPos.Mutable mutable = new BlockPos.Mutable();

            //layers = 6 + (int)(rainGradient * 4);
            //layers = 10;

            for(int offsetPosZ = playerPosZ - layers; offsetPosZ <= playerPosZ + layers; ++offsetPosZ) { // loop over the Z positions before and after the player
                for(int offsetPosX = playerPosX - layers; offsetPosX <= playerPosX + layers; ++offsetPosX) { // loop over the X positions before and after the player
                    int p = (offsetPosZ - playerPosZ + 16) * 32 + offsetPosX - playerPosX + 16;

                    double d = (double)this.field_20794[p] * 0.5;
                    double e = (double)this.field_20795[p] * 0.5;

                    mutable.set(offsetPosX, cameraY, offsetPosZ);
                    Biome biome = world.getBiome(mutable).value();
                    int worldTopY = world.getTopY(Heightmap.Type.MOTION_BLOCKING, offsetPosX, offsetPosZ);
                    int r = playerPosY - layers;
                    int s = playerPosY + layers;
                    if (r < worldTopY) {
                        r = worldTopY;
                    }

                    if (s < worldTopY) {
                        s = worldTopY;
                    }

                    int topY = Math.max(worldTopY, playerPosY);

                    if (r != s) {
                        Random random = Random.create((long)(offsetPosX * offsetPosX * 3121 + offsetPosX * 45238971 ^ offsetPosZ * offsetPosZ * 418711 + offsetPosZ * 13761));
                        mutable.set(offsetPosX, r, offsetPosZ);
                        Biome.Precipitation precipitation = biome.getPrecipitation(mutable);
                        float textureSpeed;
                        float opacity;
                        if (precipitation == Biome.Precipitation.RAIN) {
                            if (m != 0) {
                                if (m >= 0) {
                                    tessellator.draw();
                                }

                                m = 0;

                                if (rainGradient <= 0.25) {
                                    //System.out.println("light" + rainGradient);
                                    RenderSystem.setShaderTexture(0, LIGHT_RAIN);
                                } else if (rainGradient >= 0.25 && rainGradient <= 0.5) {
                                    //System.out.println("med" + rainGradient);
                                    RenderSystem.setShaderTexture(0, MEDIUM_RAIN);
                                } else if (rainGradient >= 0.5 && rainGradient <= 0.75) {
                                    //System.out.println("reg" + rainGradient);
                                    RenderSystem.setShaderTexture(0, RAIN);
                                } else {
                                    //System.out.println("heavy" + rainGradient);
                                    RenderSystem.setShaderTexture(0, HEAVY_RAIN);
                                }

                                bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
                            }

                            // TODO:
                            //RenderSystem.setShaderTexture(0, DEBUG);

                            int u = this.ticks + offsetPosX * offsetPosX * 3121 + offsetPosX * 45238971 + offsetPosZ * offsetPosZ * 418711 + offsetPosZ * 13761 & 31;
                            //h = -((float)u + tickDelta) / 32.0F * (((timer/12)+1.5F) + random.nextFloat()); // default value = 3.0, changing this to 2 slows it, changing it to 3 makes it faster
                            textureSpeed = -((float)u + tickDelta) / 32.0F * (2.1F + random.nextFloat()); // default value = 3.0, changing this to 2 slows it, changing it to 3 makes it faster
                            double v = (double)offsetPosX + 0.5 - cameraX;
                            double w = (double)offsetPosZ + 0.5 - cameraZ;
                            float x = (float)Math.sqrt(v * v + w * w) / (float)layers;
                            opacity = ((1.0F - x * x) * 0.5F + 0.5F) * rainGradient;
                            //if (Math.random() > 0.9) {
                            //    System.out.println("opacity: " + opacity);
                            //}

                            mutable.set(offsetPosX, topY, offsetPosZ);
                            int z = WorldRenderer.getLightmapCoordinates(world, mutable);

                            bufferBuilder.vertex(positionMatrix, (float) ((double)offsetPosX - cameraX - d + 0.5), (float) ((double)s - cameraY), (float) ((double)offsetPosZ - cameraZ - e + 0.5)).texture(0.0F, (float)r * 0.25F + textureSpeed).color(1.0F, 1.0F, 1.0F, opacity).light(z).next();
                            bufferBuilder.vertex(positionMatrix, (float) ((double)offsetPosX - cameraX + d + 0.5), (float) ((double)s - cameraY), (float) ((double)offsetPosZ - cameraZ + e + 0.5)).texture(1.0F, (float)r * 0.25F + textureSpeed).color(1.0F, 1.0F, 1.0F, opacity).light(z).next();
                            bufferBuilder.vertex(positionMatrix, (float) ((double)offsetPosX - cameraX + d + 0.5), (float) ((double)r - cameraY), (float) ((double)offsetPosZ - cameraZ + e + 0.5)).texture(1.0F, (float)s * 0.25F + textureSpeed).color(1.0F, 1.0F, 1.0F, opacity).light(z).next();
                            bufferBuilder.vertex(positionMatrix, (float) ((double)offsetPosX - cameraX - d + 0.5), (float) ((double)r - cameraY), (float) ((double)offsetPosZ - cameraZ - e + 0.5)).texture(0.0F, (float)s * 0.25F + textureSpeed).color(1.0F, 1.0F, 1.0F, opacity).light(z).next();

                        } else {
                            if (m != 1) {
                                if (m >= 0) {
                                    tessellator.draw();
                                }

                                m = 1;

                                if(precipitation == Biome.Precipitation.SNOW) {
                                    if (rainGradient <= 0.25) {
                                        //System.out.println("light" + rainGradient);
                                        RenderSystem.setShaderTexture(0, LIGHT_SNOW);
                                    } else if (rainGradient >= 0.25 && rainGradient <= 0.5) {
                                        //System.out.println("med" + rainGradient);
                                        RenderSystem.setShaderTexture(0, MEDIUM_SNOW);
                                    } else if (rainGradient >= 0.5 && rainGradient <= 0.75) {
                                        //System.out.println("reg" + rainGradient);
                                        RenderSystem.setShaderTexture(0, SNOW);
                                    } else {
                                        //System.out.println("heavy" + rainGradient);
                                        RenderSystem.setShaderTexture(0, HEAVY_SNOW);
                                    }
                                } else {
                                    RenderSystem.setShaderTexture(0, LIGHT_SAND);
                                }


                                //RenderSystem.setShaderTexture(0, LIGHT_SNOW);
                                bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
                            }

                            float aa = ((float)(this.ticks & 511) + tickDelta) / 512.0F; //used to be negative
                            textureSpeed = (float)(random.nextDouble() + (double)smoothTicks * (0.0008) * (double)((float)random.nextGaussian())); // this was 0.01 // horizontal motion
                            float textureVerticalSpeed = (float)(random.nextDouble() + (double)(smoothTicks * (float)random.nextGaussian()) * 0.00009);// this was 0.001 // vertical motion
                            double ac = (double)offsetPosX + 0.5 - cameraX;
                            double ad = (double)offsetPosZ + 0.5 - cameraZ;
                            opacity = (float)Math.sqrt(ac * ac + ad * ad) / (float)layers;
                            float snowOpacity = ((1.0F - opacity * opacity) * 0.3F + 0.5F) * rainGradient;
                            mutable.set(offsetPosX, topY, offsetPosZ);
                            int af = WorldRenderer.getLightmapCoordinates(world, mutable);
                            int ag = af >> 16 & '\uffff';
                            int ah = af & '\uffff';
                            int ai = (ag * 3 + 240) / 4;
                            int aj = (ah * 3 + 240) / 4;
                            bufferBuilder.vertex((double)offsetPosX - cameraX - d + 0.5, (double)s - cameraY, (double)offsetPosZ - cameraZ - e + 0.5).texture(0.0F + textureSpeed, (float)r * 0.25F + aa + textureVerticalSpeed).color(0.8F, 0.4F, 0.9F, snowOpacity).light(aj, ai).next();// 0.8F, 0.4F, 0.2F for red sand
                            bufferBuilder.vertex((double)offsetPosX - cameraX + d + 0.5, (double)s - cameraY, (double)offsetPosZ - cameraZ + e + 0.5).texture(1.0F + textureSpeed, (float)r * 0.25F + aa + textureVerticalSpeed).color(0.8F, 0.4F, 0.9F, snowOpacity).light(aj, ai).next();// 0.8F, 0.4F, 0.2F for red sand
                            bufferBuilder.vertex((double)offsetPosX - cameraX + d + 0.5, (double)r - cameraY, (double)offsetPosZ - cameraZ + e + 0.5).texture(1.0F + textureSpeed, (float)s * 0.25F + aa + textureVerticalSpeed).color(0.8F, 0.4F, 0.9F, snowOpacity).light(aj, ai).next();// 0.8F, 0.4F, 0.2F for red sand
                            bufferBuilder.vertex((double)offsetPosX - cameraX - d + 0.5, (double)r - cameraY, (double)offsetPosZ - cameraZ - e + 0.5).texture(0.0F + textureSpeed, (float)s * 0.25F + aa + textureVerticalSpeed).color(0.8F, 0.4F, 0.9F, snowOpacity).light(aj, ai).next();// 0.8F, 0.4F, 0.2F for red sand
                        }
                    }
                }
            }

            if (m >= 0) {
                tessellator.draw();
                //matrixStack.pop();
                //RenderSystem.applyModelViewMatrix();
            }
            //matrixStack.pop();///
            //RenderSystem.applyModelViewMatrix();////

            RenderSystem.enableCull();
            RenderSystem.disableBlend();
            manager.disable();
        }
    }


    /**
     * @author Andrew6rant (Andrew Grant)
     * @reason Make a big overwrite for ease of use, I am later going to refine it into multiple injects for compatibility
     */
    @Overwrite
    public void tickRainSplashing(Camera camera) {
        float f = this.client.world.getRainGradient(1.0F) / (MinecraftClient.isFancyGraphicsOrBetter() ? 1.0F : 2.0F);
        if (!(f <= 0.0F)) {
            float timer = (System.currentTimeMillis() % 10000) / 10000f * 40f;
            Random random = Random.create((long)this.ticks * 312987231L);
            WorldView worldView = this.client.world;
            BlockPos blockPos = BlockPos.ofFloored(camera.getPos());
            BlockPos blockPos2 = null;
            int i = (int)(100.0F * f * f) / (this.client.options.getParticles().getValue() == ParticlesMode.DECREASED ? 2 : 1);

            for(int j = 0; j < ((timer*3)+8); ++j) { // used to be i
                int k = random.nextInt(21) - 10;
                int l = random.nextInt(21) - 10;
                //System.out.println("Random, i: "+i+", l: "+l);
                BlockPos blockPos3 = worldView.getTopPosition(Heightmap.Type.MOTION_BLOCKING, blockPos.add(k, 0, l));
                if (blockPos3.getY() > worldView.getBottomY() && blockPos3.getY() <= blockPos.getY() + 10 && blockPos3.getY() >= blockPos.getY() - 10) {
                    Biome biome = (Biome)worldView.getBiome(blockPos3).value();
                    if (biome.getPrecipitation(blockPos3) == Biome.Precipitation.RAIN) {
                        blockPos2 = blockPos3.down();
                        if (this.client.options.getParticles().getValue() == ParticlesMode.MINIMAL) {
                            break;
                        }

                        double d = random.nextDouble();
                        double e = random.nextDouble();
                        BlockState blockState = worldView.getBlockState(blockPos2);
                        FluidState fluidState = worldView.getFluidState(blockPos2);
                        VoxelShape voxelShape = blockState.getCollisionShape(worldView, blockPos2);
                        double g = voxelShape.getEndingCoord(Direction.Axis.Y, d, e);
                        double h = (double)fluidState.getHeight(worldView, blockPos2);
                        double m = Math.max(g, h);
                        ParticleEffect particleEffect = !fluidState.isIn(FluidTags.LAVA) && !blockState.isOf(Blocks.MAGMA_BLOCK) && !CampfireBlock.isLitCampfire(blockState) ? ParticleTypes.RAIN : ParticleTypes.SMOKE;
                        this.client.world.addParticle(particleEffect, (double)blockPos2.getX() + d, (double)blockPos2.getY() + m, (double)blockPos2.getZ() + e, 0.0, 0.0, 0.0);
                    }
                }
            }

            if (blockPos2 != null && random.nextInt(3) < this.rainSoundCounter++) {
                this.rainSoundCounter = 0;
                if (blockPos2.getY() > blockPos.getY() + 1 && worldView.getTopPosition(Heightmap.Type.MOTION_BLOCKING, blockPos).getY() > MathHelper.floor((float)blockPos.getY())) {
                    this.client.world.playSoundAtBlockCenter(blockPos2, SoundEvents.WEATHER_RAIN_ABOVE, SoundCategory.WEATHER, 0.1F, 0.5F, false);
                } else {
                    this.client.world.playSoundAtBlockCenter(blockPos2, SoundEvents.WEATHER_RAIN, SoundCategory.WEATHER, 0.2F, 1.0F, false);
                }
            }

        }
    }
}
