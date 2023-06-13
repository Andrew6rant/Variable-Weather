package io.github.andrew6rant;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VariableWeather implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("variable-weather");

	public static final Block WEATHER_VANE = new VaneBlock(FabricBlockSettings.copyOf(Blocks.STONE));

	public static BlockEntityType<VaneBlockEntity> VANE_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier("variable-weather","weather_vane"),
			FabricBlockEntityTypeBuilder.create(VaneBlockEntity::new, WEATHER_VANE).build());

	@Override
	public void onInitialize() {
		Registry.register(Registries.BLOCK, new Identifier("variable-weather", "weather_vane"), WEATHER_VANE);
	}
}