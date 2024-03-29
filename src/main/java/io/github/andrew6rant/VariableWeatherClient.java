package io.github.andrew6rant;

import io.github.andrew6rant.weather.WeatherAccess;
import io.github.andrew6rant.weather.WeatherData;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class VariableWeatherClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(VariableWeather.WEATHER_VANE, RenderLayer.getCutout());
        BlockEntityRendererFactories.register(VariableWeather.VANE_BLOCK_ENTITY, VaneBlockEntityRenderer::new);

        ClientPlayNetworking.registerGlobalReceiver(WeatherData.WEATHER_UPDATE_PACKET_ID, (client, handler, buf, responseSender) -> {
            WeatherData weatherData = WeatherData.fromBuf(buf);

            client.execute(() -> ((WeatherAccess) (client.world)).getWeatherData().copy(weatherData));
        });

    }

    public static boolean renderWindVisualization() {
        return true;
    }
}
