package io.github.andrew6rant.mixin;

import io.github.andrew6rant.weather.WeatherAccess;
import io.github.andrew6rant.weather.WeatherData;
import io.github.andrew6rant.weather.WeatherManager;
import io.github.andrew6rant.weather.WeatherManagerAccess;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.RandomSequencesState;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.storage.LevelStorage;
import net.minecraft.world.spawner.Spawner;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/**
 *
 * @author LudoCrypt
 *
 *         Implementation of {@link WeatherManagerAccess} to supply the Hiemal
 *         weather manager, as well as {@link WeatherAccess} for supplying data.
 *
 */
@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World implements WeatherManagerAccess, WeatherAccess {

    @Unique
    private WeatherManager weatherManager;

    protected ServerWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, DynamicRegistryManager registryManager, RegistryEntry<DimensionType> dimensionEntry, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long biomeAccess, int maxChainedNeighborUpdates) {
        super(properties, registryRef, registryManager, dimensionEntry, profiler, isClient, debugWorld, biomeAccess, maxChainedNeighborUpdates);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void frostyHeights$init(MinecraftServer server, Executor workerExecutor, LevelStorage.Session session, ServerWorldProperties properties, RegistryKey worldKey, DimensionOptions dimensionOptions, WorldGenerationProgressListener worldGenerationProgressListener, boolean debugWorld, long seed, List spawners, boolean shouldTickTime, RandomSequencesState randomSequencesState, CallbackInfo ci) {
        this.weatherManager = this.getPersistentStateManager().getOrCreate(nbtCompound -> WeatherManager.fromNbt(((ServerWorld) (Object) this), nbtCompound), () -> new WeatherManager(((ServerWorld) (Object) this)), "overworld_weather");
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V", ordinal = 0, shift = At.Shift.BEFORE))
    private void frostyHeights$tick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        this.getProfiler().swap("Frosty Heights Weather");
        this.getWeatherManager().tick();
    }

    @Override
    public WeatherManager getWeatherManager() {
        return weatherManager;
    }

    @Unique
    @Override
    public WeatherData getWeatherData() {
        return this.getWeatherManager().getWeatherData();
    }

    @Shadow
    public abstract PersistentStateManager getPersistentStateManager();

}
