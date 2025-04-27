package com.mikitellurium.telluriumforge.capability;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.common.extensions.ILevelExtension;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * Interface used to get optional version of block capabilities
 */
public interface OptionalBlockCapability {

    default <T, C> Optional<T> getOptionalCap(ILevelExtension level, BlockCapability<T, C> cap, BlockPos pos, C context) {
        return Optional.ofNullable(level.getCapability(cap, pos, context));
    }

    default <T, C> Optional<T> getOptionalCap(ILevelExtension level, BlockCapability<T, C> cap, BlockPos pos, @Nullable BlockState state, @Nullable BlockEntity blockEntity, C context) {
        return Optional.ofNullable(level.getCapability(cap, pos, state, blockEntity, context));
    }

    default <T> Optional<T> getOptionalCap(ILevelExtension level, BlockCapability<T, @Nullable Void> cap, BlockPos pos) {
        return Optional.ofNullable(level.getCapability(cap, pos));
    }

    default <T> Optional<T> getOptionalCap(ILevelExtension level, BlockCapability<T, @Nullable Void> cap, BlockPos pos, @Nullable BlockState state, @Nullable BlockEntity blockEntity) {
        return Optional.ofNullable(level.getCapability(cap, pos, state, blockEntity));
    }

}
