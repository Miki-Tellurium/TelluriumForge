package com.mikitellurium.telluriumforge.capability;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.BooleanSupplier;

/**
 * Wrapper class of {@link BlockCapabilityCache} that allows to retrieve the capability
 * as an {@link Optional}.
 */
public class BlockCapCacheWrapper<T, C extends @Nullable Object> {

    private final BlockCapabilityCache<T, C> cache;

    public static <T, C> BlockCapCacheWrapper<T, C> create(BlockCapability<T, C> capability, ServerLevel level, BlockPos pos, C context) {
        return create(capability, level, pos, context, () -> true, () -> {});
    }

    public static <T, C> BlockCapCacheWrapper<T, C> create(BlockCapability<T, C> capability, ServerLevel level, BlockPos pos, C context, BooleanSupplier isValid, Runnable invalidationListener) {
        return create(BlockCapabilityCache.create(capability, level, pos, context, isValid, invalidationListener));
    }

    public static <T, C> BlockCapCacheWrapper<T, C> create(BlockCapabilityCache<T, C> cache) {
        return new BlockCapCacheWrapper<>(cache);
    }

    private BlockCapCacheWrapper(BlockCapabilityCache<T, C> cache) {
        this.cache = cache;
    }

    public BlockCapabilityCache<T, C> cache() {
        return this.cache;
    }

    public ServerLevel level() {
        return this.cache.level();
    }

    public BlockPos pos() {
        return this.cache.pos();
    }

    public C context() {
        return this.cache.context();
    }

    public Optional<T> getOptional() {
        return Optional.ofNullable(this.cache.getCapability());
    }

    public T getCapability() {
        return this.cache.getCapability();
    }

}
