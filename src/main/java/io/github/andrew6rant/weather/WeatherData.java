package io.github.andrew6rant.weather;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import io.github.andrew6rant.weather.WeatherEnum.WeatherSettings;

// Massive thanks to LudoCrypt for some of this code
// https://github.com/LudoCrypt/Frosty-Heights/blob/main/src/main/java/net/ludocrypt/frostyheights/weather/FrostyHeightsWeatherData.java
public class WeatherData {
    public static final Identifier WEATHER_UPDATE_PACKET_ID = new Identifier("variable-weather", "weather_update");

    private int ticksUntilNextWeather = 0;
    private WeatherEnum currentWeather = WeatherEnum.CLEAR;
    private WeatherEnum nextWeather = WeatherEnum.UNDETERMINED;

    private long amplitudeSeed = 1337L;
    private long directionSeed = 1337L;

    /* Y level for wind noise sampling */
    private double windDelta = 0.0D;

    /* Client */
    private double prevWindDelta = 0.0D;

    private WeatherEnum prevWeather = WeatherEnum.CLEAR;

    private WeatherSettings weatherSettings = WeatherEnum.CLEAR.cloneSettings();
    private WeatherSettings prevWeatherSettings = WeatherEnum.CLEAR.cloneSettings();

    public void sendToClient(World world) {
        if (!world.isClient) {
            PacketByteBuf buf = PacketByteBufs.create();

            buf.writeInt(this.getTicksUntilNextWeather());
            buf.writeByte((byte) this.getCurrentWeather().ordinal());
            buf.writeByte((byte) this.getNextWeather().ordinal());

            buf.writeLong(this.amplitudeSeed);
            buf.writeLong(this.directionSeed);

            buf.writeDouble(this.getWindDelta());

            buf.writeDouble(this.getPrevWindDelta());

            buf.writeByte((byte) this.getPrevWeather().ordinal());

            this.getWeatherSettings().writeBuf(buf);
            this.getPrevWeatherSettings().writeBuf(buf);

            for (ServerPlayerEntity player : PlayerLookup.world((ServerWorld) world)) {
                ServerPlayNetworking.send(player, WEATHER_UPDATE_PACKET_ID, buf);
            }
        }
    }

    public static WeatherData fromBuf(PacketByteBuf buf) {
        WeatherData data = new WeatherData();

        data.setTicksUntilNextWeather(buf.readInt());
        data.setCurrentWeather(WeatherEnum.values()[buf.readByte()]);
        data.setNextWeather(WeatherEnum.values()[buf.readByte()]);

        data.amplitudeSeed = buf.readLong();
        data.directionSeed = buf.readLong();

        data.setWindDelta(buf.readDouble());

        data.setPrevWindDelta(buf.readDouble());

        data.setPrevWeather(WeatherEnum.values()[buf.readByte()]);

        data.setWeatherSettings(WeatherEnum.WeatherSettings.fromBuf(buf));
        data.setPrevWeatherSettings(WeatherSettings.fromBuf(buf));

        return data;
    }

    public static WeatherData fromNbt(ServerWorld world, NbtCompound nbt) {
        WeatherData data = new WeatherData();

        data.setTicksUntilNextWeather(nbt.getInt("ticksUntilNextWeather"));
        data.setCurrentWeather(WeatherEnum.values()[nbt.getByte("currentWeather")]);
        data.setNextWeather(WeatherEnum.values()[nbt.getByte("nextWeather")]);

        data.amplitudeSeed = nbt.getLong("amplitudeSeed");
        data.directionSeed = nbt.getLong("directionSeed");

        data.setWindDelta(nbt.getDouble("windDelta"));

        data.setNextWeather(data.getCurrentWeather());

        data.setWeatherSettings(data.getCurrentWeather().cloneSettings());
        data.setPrevWeatherSettings(data.getCurrentWeather().cloneSettings());

        return data;
    }

    public static NbtCompound writeNbt(WeatherData manager, NbtCompound nbt) {

        nbt.putInt("ticksUntilNextWeather", manager.getTicksUntilNextWeather());
        nbt.putByte("currentWeather", (byte) manager.getCurrentWeather().ordinal());
        nbt.putByte("nextWeather", (byte) manager.getNextWeather().ordinal());

        nbt.putLong("amplitudeSeed", manager.amplitudeSeed);
        nbt.putLong("directionSeed", manager.directionSeed);

        nbt.putDouble("windDelta", manager.getWindDelta());

        return nbt;
    }

    public void copy(WeatherData data) {
        this.setTicksUntilNextWeather(data.getTicksUntilNextWeather());
        this.setCurrentWeather(data.getCurrentWeather());
        this.setNextWeather(data.getNextWeather());

        this.amplitudeSeed = data.amplitudeSeed;
        this.directionSeed = data.directionSeed;

        this.setWindDelta(data.getWindDelta());

        this.setPrevWindDelta(data.getPrevWindDelta());

        this.setPrevWeather(data.getPrevWeather());

        this.getWeatherSettings().copy(data.getWeatherSettings());
        this.getPrevWeatherSettings().copy(data.getPrevWeatherSettings());
    }

    public boolean isSnowing() {
        return this.getCurrentWeather().equals(WeatherEnum.SNOW);
    }

    public boolean isBlizzard() {
        return this.getCurrentWeather().equals(WeatherEnum.BLIZZARD);
    }

    public boolean isSnowingOrBlizzard() {
        return this.isSnowing() || isBlizzard();
    }

    public double getWindDelta(float tickDelta) {
        return MathHelper.lerp(tickDelta, this.getPrevWindDelta(), this.getWindDelta());
    }

    public double getWindAmplitude(float tickDelta) {
        return MathHelper.lerp(tickDelta, this.getPrevWeatherSettings().getWindAmplitude(), this.getWeatherSettings().getWindAmplitude());
    }

    public double getWindVelocity(float tickDelta) {
        return MathHelper.lerp(tickDelta, this.getPrevWeatherSettings().getWindVelocity(), this.getWeatherSettings().getWindVelocity());
    }

    public double getDarknessScalar(float tickDelta) {
        return MathHelper.lerp(tickDelta, this.getPrevWeatherSettings().getDarknessScalar(), this.getWeatherSettings().getDarknessScalar());
    }

    public WeatherEnum getPrevWeather() {
        return prevWeather;
    }

    public WeatherEnum getCurrentWeather() {
        return this.currentWeather;
    }

    public WeatherEnum getNextWeather() {
        return this.nextWeather;
    }

    public int getTicksUntilNextWeather() {
        return this.ticksUntilNextWeather;
    }

    public double getWindDelta() {
        return this.windDelta;
    }

    public double getPrevWindDelta() {
        return this.prevWindDelta;
    }

    public WeatherEnum.WeatherSettings getWeatherSettings() {
        return this.weatherSettings;
    }

    public WeatherEnum.WeatherSettings getPrevWeatherSettings() {
        return this.prevWeatherSettings;
    }

    public long getAmplitudeSeed() {
        return amplitudeSeed;
    }

    public long getDirectionSeed() {
        return directionSeed;
    }

    public void setPrevWeather(WeatherEnum prevWeather) {
        this.prevWeather = prevWeather;
    }

    public void setCurrentWeather(WeatherEnum weather) {
        this.currentWeather = weather;
    }

    public void setNextWeather(WeatherEnum weather) {
        this.nextWeather = weather;
    }

    public void setTicksUntilNextWeather(int ticks) {
        this.ticksUntilNextWeather = ticks;
    }

    public void setWindDelta(double windDelta) {
        this.windDelta = windDelta;
    }

    public void setPrevWindDelta(double prevWindDelta) {
        this.prevWindDelta = prevWindDelta;
    }

    public void setWeatherSettings(WeatherSettings weatherSettings) {
        this.weatherSettings = weatherSettings;
    }

    public void setPrevWeatherSettings(WeatherSettings prevWeatherSettings) {
        this.prevWeatherSettings = prevWeatherSettings;
    }

    public void setAmplitudeSeed(long amplitudeSeed) {
        this.amplitudeSeed = amplitudeSeed;
    }

    public void setDirectionSeed(long directionSeed) {
        this.directionSeed = directionSeed;
    }

}
