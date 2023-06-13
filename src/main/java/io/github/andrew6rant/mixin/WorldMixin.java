package io.github.andrew6rant.mixin;

import io.github.andrew6rant.weather.WeatherAccess;
import io.github.andrew6rant.weather.WeatherData;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(World.class)
public class WorldMixin implements WeatherAccess {
    @Unique
    private WeatherData weatherData = new WeatherData();
    @Unique
    @Override
    public WeatherData getWeatherData() {
        return this.weatherData;
    }
}
