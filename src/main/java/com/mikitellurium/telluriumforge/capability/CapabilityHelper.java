package com.mikitellurium.telluriumforge.capability;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;
import net.neoforged.neoforge.common.extensions.ILevelExtension;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * Used to get optional version of capabilities
 */
public class CapabilityHelper {

    public static <T, C> Optional<T> getOptional(ILevelExtension level, BlockCapability<T, C> cap, BlockPos pos, C context) {
        return Optional.ofNullable(level.getCapability(cap, pos, context));
    }

    public static <T, C> Optional<T> getOptional(ILevelExtension level, BlockCapability<T, C> cap, BlockPos pos, @Nullable BlockState state, @Nullable BlockEntity blockEntity, C context) {
        return Optional.ofNullable(level.getCapability(cap, pos, state, blockEntity, context));
    }

    public static <T> Optional<T> getOptional(ILevelExtension level, BlockCapability<T, @Nullable Void> cap, BlockPos pos) {
        return Optional.ofNullable(level.getCapability(cap, pos));
    }

    public static <T> Optional<T> getOptional(ILevelExtension level, BlockCapability<T, @Nullable Void> cap, BlockPos pos, @Nullable BlockState state, @Nullable BlockEntity blockEntity) {
        return Optional.ofNullable(level.getCapability(cap, pos, state, blockEntity));
    }

    public static <T, C> Optional<T> getOptional(ItemStack itemStack, ItemCapability<T, C> cap, C context) {
        return Optional.ofNullable(itemStack.getCapability(cap, context));
    }

    public static <T> Optional<T> getOptional(ItemStack itemStack, ItemCapability<T, Void> cap) {
        return Optional.ofNullable(itemStack.getCapability(cap));
    }

    public static <T, C> Optional<T> getOptional(Entity entity, EntityCapability<T, C> cap, C context) {
        return Optional.ofNullable(entity.getCapability(cap, context));
    }

    public static <T> Optional<T> getOptional(Entity entity, EntityCapability<T, Void> cap) {
        return Optional.ofNullable(entity.getCapability(cap));
    }

}
