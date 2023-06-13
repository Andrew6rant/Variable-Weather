package io.github.andrew6rant;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class VariableWeatherClient implements ClientModInitializer {
    public static final EntityModelLayer MODEL_CHIMES_LAYER = new EntityModelLayer(new Identifier("variable-weather", "weather_vane"), "vane");
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(VariableWeather.WEATHER_VANE, RenderLayer.getCutout());
        BlockEntityRendererFactories.register(VariableWeather.VANE_BLOCK_ENTITY, VaneBlockEntityRenderer::new);
    }

    //static {
    //    BlockEntityRendererFactories.register(VariableWeather.VANE_BLOCK_ENTITY, ctx -> new VaneBlockEntityRenderer(ctx));
    //}
}
