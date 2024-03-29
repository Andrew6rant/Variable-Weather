package io.github.andrew6rant;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec2f;

import static io.github.andrew6rant.weather.WeatherManager.getWindPolar;

public class VaneBlockEntityRenderer implements BlockEntityRenderer<VaneBlockEntity> {
    private final ModelPart platform;
    private final ModelPart spinner;
    public VaneBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        ModelData platformData = new ModelData();
        ModelData spinnerData = new ModelData();
        ModelPartData platformPartData = platformData.getRoot();
        ModelPartData spinnerPartData = spinnerData.getRoot();

        // I had to make this manually, and took me absolutely ages lol
        spinnerPartData.addChild("head", ModelPartBuilder.create()
            .uv(0, 0).cuboid(
                -2f, 15f, 3f,
                4f, 6f, 3f), ModelTransform.NONE)
        .addChild("bill", ModelPartBuilder.create()
            .uv(14, 0).cuboid(
                -2f, 17f, 6f,
                4f, 2f, 2f), ModelTransform.NONE)
        .addChild("body", ModelPartBuilder.create()
            .uv(0, 9).cuboid(
                -3f, 11f, -4f,
                6f, 6f, 8f), ModelTransform.NONE)
        .addChild("left_wing", ModelPartBuilder.create()
            .uv(28, 13).cuboid(
                -4f, 13f, -3f,
                1f, 4f, 6f), ModelTransform.NONE)
        .addChild("right_wing", ModelPartBuilder.create()
            .uv(28, 13).cuboid(
                3f, 13f, -3f,
                1f, 4f, 6f), ModelTransform.NONE)
        .addChild("frill", ModelPartBuilder.create()
            .uv(44, 21).cuboid(
                0f, 15f, 4f,
                0f, 8f, 3f), ModelTransform.NONE)
        .addChild("arrow", ModelPartBuilder.create()
            .uv(0, 33).cuboid(
                0f, 6f, -11f,
                0f, 5f, 22f), ModelTransform.NONE);

        platformPartData.addChild("east-west", ModelPartBuilder.create()
            .uv(32, 0).cuboid(
                0f, 2f, 8f,
                16f, 5f, 0f), ModelTransform.NONE)
        .addChild("north-south", ModelPartBuilder.create()
            .uv(32, -11).cuboid(
                8f, 2f, 0f,
                0f, 5f, 16f), ModelTransform.NONE)
        .addChild("stand", ModelPartBuilder.create()
            .uv(0, 0).cuboid(
                7f, 0f, 7f,
                2f, 6f, 2f), ModelTransform.NONE);

        this.platform = platformPartData.createPart(64, 32);
        this.spinner = spinnerPartData.createPart(64, 32);
        this.spinner.setPivot(8f, 0f, 8f);
    }

    @Override
    public void render(VaneBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        if(entity.hasWorld()) {
            Vec2f wind = getWindPolar(entity.getWorld(), entity.getPos().toCenterPos());
            matrices.translate(0.5D, 0, 0.5D);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(wind.y));
            matrices.translate(-0.5D, 0, -0.5D);
        }
        VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(new Identifier("variable-weather", "textures/entity/vane_debug.png")));
        this.spinner.render(matrices, consumer, light, overlay);
        matrices.pop();
        this.platform.render(matrices, consumer, light, overlay);
    }
}
