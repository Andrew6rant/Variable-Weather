package io.github.andrew6rant;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static io.github.andrew6rant.VariableWeather.VANE_BLOCK_ENTITY;

public class VaneBlockEntity extends BlockEntity {


    public VaneBlockEntity(BlockPos pos, BlockState state) {
        super(VANE_BLOCK_ENTITY, pos, state);
    }


    @Override
    public boolean onSyncedBlockEvent(int type, int data) {
        return super.onSyncedBlockEvent(type, data);
    }

    public static void tick(World world, BlockPos pos, BlockState state, VaneBlockEntity vane) {
        //if(world.isClient) {
//
        //}
    }
}