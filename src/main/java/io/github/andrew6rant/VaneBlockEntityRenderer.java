package io.github.andrew6rant;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class VaneBlockEntityRenderer implements BlockEntityRenderer<VaneBlockEntity> {
    private final ModelPart platform;
    public VaneBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        ModelData platformData = new ModelData();
        ModelPartData platformPartData = platformData.getRoot();

        // I had to make this manually, and it took me ages lol
        platformPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(
                6f, 15f, 3f,
                4f, 6f, 3f), ModelTransform.NONE);
        platformPartData.addChild("bill", ModelPartBuilder.create().uv(14, 0).cuboid(
                6f, 17f, 1f,
                4f, 2f, 2f), ModelTransform.NONE);
        platformPartData.addChild("body", ModelPartBuilder.create().uv(0, 9).cuboid(
                5f, 11f, 5f,
                6f, 6f, 8f), ModelTransform.NONE);
        platformPartData.addChild("left_wing", ModelPartBuilder.create().uv(28, 13).cuboid(
                4f, 13f, 6f,
                1f, 4f, 6f), ModelTransform.NONE);
        platformPartData.addChild("right_wing", ModelPartBuilder.create().uv(28, 13).cuboid(
                11f, 13f, 6f,
                1f, 4f, 6f), ModelTransform.NONE);
        platformPartData.addChild("frill", ModelPartBuilder.create().uv(44, 21).cuboid(
                8f, 15f, 2f,
                0f, 8f, 3f), ModelTransform.NONE);
        platformPartData.addChild("arrow", ModelPartBuilder.create().uv(0, 33).cuboid(
                8f, 6f, -3f,
                0f, 5f, 22f), ModelTransform.NONE);
        platformPartData.addChild("east-west", ModelPartBuilder.create().uv(32, 0).cuboid(
                0f, 2f, 8f,
                16f, 5f, 0f), ModelTransform.NONE);
        platformPartData.addChild("north-south", ModelPartBuilder.create().uv(32, -11).cuboid(
                8f, 2f, 0f,
                0f, 5f, 16f), ModelTransform.NONE);
        platformPartData.addChild("stand", ModelPartBuilder.create().uv(0, 0).cuboid(
                7f, 0f, 7f,
                2f, 6f, 2f), ModelTransform.NONE);
        this.platform = platformPartData.createPart(64, 32);
        //this.platform.setPivot(0f, 0f, 0f);
    }

    @Override
    public void render(VaneBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if(entity.hasWorld()) {
            //float correctedTicks = (float) ((entity.getWorld().getTime() % 314.15) + tickDelta);
            //this.platform.yaw = correctedTicks * 0.05f;
        }
        VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(new Identifier("variable-weather", "textures/entity/vane_debug.png")));
        this.platform.render(matrices, consumer, light, overlay);
    }
}
