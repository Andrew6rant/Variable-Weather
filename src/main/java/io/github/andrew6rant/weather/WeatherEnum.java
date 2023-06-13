package io.github.andrew6rant.weather;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.random.Random;


// Massive thanks to LudoCrypt for some of this code.
// https://github.com/LudoCrypt/Frosty-Heights/blob/main/src/main/java/net/ludocrypt/frostyheights/weather/FrostyHeightsWeather.java
public enum WeatherEnum {
    UNDETERMINED(0, 0, new WeatherSettings()),
    CLEAR(36000, 48000, new WeatherSettings(1D, 1D, 1D)),
    NORMAL(24000, 48000, new WeatherSettings(2D, 2D, 2D)),
    SNOW(18000, 48000, new WeatherSettings(3D, 3D, 3D)),
    WIND(18000, 36000, new WeatherSettings(4D, 4D, 4D)),
    BLIZZARD(12000, 24000, new WeatherSettings(5D, 5D, 5D));

    private final int minTime;
    private final int maxTime;

    private final WeatherSettings weatherSettings;

    private WeatherEnum(int minTime, int maxTime, WeatherSettings weatherSettings) {
        this.minTime = minTime;
        this.maxTime = maxTime;
        this.weatherSettings = weatherSettings;
    }

    public int getMinTime() {
        return this.minTime;
    }

    public int getMaxTime() {
        return this.maxTime;
    }

    public WeatherSettings cloneSettings() {
        return this.equals(UNDETERMINED) ? new WeatherSettings() : new WeatherSettings(this.weatherSettings.getWindAmplitude(), this.weatherSettings.getWindVelocity(), this.weatherSettings.getDarknessScalar());
    }

    public WeatherEnum getNext(Random random) {
        double d = random.nextDouble();
        switch (this) {
            case CLEAR:
                return WeatherEnum.NORMAL;
            case NORMAL:
                if (d < 0.4) {
                    return WeatherEnum.SNOW;
                } else if (d < 0.8) {
                    return WeatherEnum.WIND;
                } else {
                    return WeatherEnum.CLEAR;
                }
            case SNOW:
                if (d < 0.2) {
                    return WeatherEnum.NORMAL;
                } else if (d < 0.4) {
                    return WeatherEnum.BLIZZARD;
                } else {
                    return WeatherEnum.WIND;
                }
            case WIND:
                if (d < 0.1) {
                    return WeatherEnum.NORMAL;
                } else if (d < 0.4) {
                    return WeatherEnum.BLIZZARD;
                } else {
                    return WeatherEnum.SNOW;
                }
            case BLIZZARD:
                if (d < 0.6) {
                    return WeatherEnum.SNOW;
                } else {
                    return WeatherEnum.WIND;
                }
            case UNDETERMINED:
            default:
                return WeatherEnum.CLEAR;
        }
    }

    public static class WeatherSettings {

        /* Is Undetermined */
        private final boolean undetermined;

        /* How loud/strong the wind is */
        private double windAmplitude;

        /* How fast the wind changes over time */
        private double windVelocity;

        /* How dark the world should be */
        private double darknessScalar;

        private WeatherSettings() {
            this.undetermined = true;
            this.windAmplitude = 0.0D;
            this.windVelocity = 0.0D;
            this.darknessScalar = 1.0D;
        }

        private WeatherSettings(double windAmplitude, double windVelocity, double darknessScalar) {
            this.undetermined = false;
            this.windAmplitude = windAmplitude;
            this.windVelocity = windVelocity;
            this.darknessScalar = darknessScalar;
        }

        public boolean isUndetermined() {
            return this.undetermined;
        }

        public double getWindAmplitude() {
            return windAmplitude;
        }

        public double getWindVelocity() {
            return windVelocity;
        }

        public double getDarknessScalar() {
            return darknessScalar;
        }

        public void setWindAmplitude(double windAmplitude) {
            this.windAmplitude = windAmplitude;
        }

        public void setWindVelocity(double windVelocity) {
            this.windVelocity = windVelocity;
        }

        public void setDarknessScalar(double darknessScalar) {
            this.darknessScalar = darknessScalar;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof WeatherSettings settings) {
                return settings.getWindAmplitude() == this.getWindAmplitude() && settings.getWindVelocity() == this.getWindVelocity() && settings.getDarknessScalar() == this.getDarknessScalar();
            }
            return false;
        }

        public static WeatherSettings fromBuf(PacketByteBuf buf) {
            double windAmplitude = buf.readDouble();
            double windVelocity = buf.readDouble();
            double darknessScalar = buf.readDouble();

            return new WeatherSettings(windAmplitude, windVelocity, darknessScalar);
        }

        public void writeBuf(PacketByteBuf buf) {
            buf.writeDouble(this.getWindAmplitude());
            buf.writeDouble(this.getWindVelocity());
            buf.writeDouble(this.getDarknessScalar());
        }

        public void copy(WeatherSettings settings) {
            this.setWindAmplitude(settings.getWindAmplitude());
            this.setWindVelocity(settings.getWindVelocity());
            this.setDarknessScalar(settings.getDarknessScalar());
        }

        public WeatherSettings clone() {
            return new WeatherSettings(this.getWindAmplitude(), this.getWindVelocity(), this.getDarknessScalar());
        }

        public void stepTowards(WeatherSettings settings, double steps) {
            this.stepWindAmplitude(settings, steps);
            this.stepWindVelocity(settings, steps);
        }

        public void stepWindAmplitude(WeatherSettings settings, double steps) {
            this.setWindAmplitude(step(steps, this.getWindAmplitude(), settings.getWindAmplitude()));
        }

        public void stepWindVelocity(WeatherSettings settings, double steps) {
            this.setWindVelocity(step(steps, this.getWindVelocity(), settings.getWindVelocity()));
        }

        public void stepDarknessScalar(WeatherSettings settings, double steps) {
            this.setDarknessScalar(step(steps, this.getDarknessScalar(), settings.getDarknessScalar()));
        }

        public static double step(double delta, double start, double end) {
            if (start == end) {
                return end;
            }
            double step = (end - start) / (delta * Math.abs(end - start));
            if (Math.abs(end - start) < (1.0D / delta)) {
                return end;
            }
            return start + step;
        }

    }

}
