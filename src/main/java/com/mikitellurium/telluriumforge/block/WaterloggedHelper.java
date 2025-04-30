package com.mikitellurium.telluriumforge.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;

public interface WaterloggedHelper extends Waterloggable {
    BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    /**
     * Return the {@link FluidState} of the block
     * @return Water if the block is waterlogged or empty
     */
    default FluidState getFluidForBlockState(BlockState blockState) {
        return blockState.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : Fluids.EMPTY.getDefaultState();
    }

    /**
     * Updates the water state
     */
    default void updateFluid(BlockState blockState, WorldAccess level, BlockPos pos) {
        if (blockState.get(WATERLOGGED)) {
            level.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(level));
        }
    }

    /**
     * Return true if the {@link FluidState} is water
     */
    default boolean shouldWaterlogOnPlacement(ItemPlacementContext context) {
        return context.getWorld().getFluidState(context.getBlockPos()).getFluid() == Fluids.WATER;
    }
}
