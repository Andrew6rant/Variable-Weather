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
    private final ModelPart spinner;
    public VaneBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        ModelData platformData = new ModelData();
        ModelData spinnerData = new ModelData();
        ModelPartData platformPartData = platformData.getRoot();
        ModelPartData spinnerPartData = spinnerData.getRoot();

        // I had to make this manually, and it took me ages lol
        spinnerPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(
                6f, 15f, 3f,
                4f, 6f, 3f), ModelTransform.NONE);
        spinnerPartData.addChild("bill", ModelPartBuilder.create().uv(14, 0).cuboid(
                6f, 17f, 1f,
                4f, 2f, 2f), ModelTransform.NONE);
        spinnerPartData.addChild("body", ModelPartBuilder.create().uv(0, 9).cuboid(
                5f, 11f, 5f,
                6f, 6f, 8f), ModelTransform.NONE);
        spinnerPartData.addChild("left_wing", ModelPartBuilder.create().uv(28, 13).cuboid(
                4f, 13f, 6f,
                1f, 4f, 6f), ModelTransform.NONE);
        spinnerPartData.addChild("right_wing", ModelPartBuilder.create().uv(28, 13).cuboid(
                11f, 13f, 6f,
                1f, 4f, 6f), ModelTransform.NONE);
        spinnerPartData.addChild("frill", ModelPartBuilder.create().uv(44, 21).cuboid(
                8f, 15f, 2f,
                0f, 8f, 3f), ModelTransform.NONE);
        spinnerPartData.addChild("arrow", ModelPartBuilder.create().uv(0, 33).cuboid(
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
        this.spinner = spinnerPartData.createPart(64, 32);
        this.spinner.setPivot(8f, 0f, 8f);
    }

    @Override
    public void render(VaneBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if(entity.hasWorld()) {
            //float correctedTicks = (float) ((entity.getWorld().getTime() % 314.15) + tickDelta);
            //this.spinner.yaw = correctedTicks * 0.05f;
        }
        VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(new Identifier("variable-weather", "textures/entity/vane_debug.png")));
        this.platform.render(matrices, consumer, light, overlay);
        this.spinner.render(matrices, consumer, light, overlay);
    }
}
