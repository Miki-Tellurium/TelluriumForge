package com.mikitellurium.telluriumforge.registry.deferred;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;

public class DeferredBlockEntityType<T extends BlockEntityType<?>> extends DeferredHolder<BlockEntityType<?>, T> {
    protected DeferredBlockEntityType(ResourceKey<BlockEntityType<?>> key) {
        super(key);
    }

    public static <T extends BlockEntityType<?>> DeferredBlockEntityType<T> createBlockEntity(ResourceLocation key) {
        return createBlockEntity(ResourceKey.create(Registries.BLOCK_ENTITY_TYPE, key));
    }

    public static <T extends BlockEntityType<?>> DeferredBlockEntityType<T> createBlockEntity(ResourceKey<BlockEntityType<?>> key) {
        return new DeferredBlockEntityType<>(key);
    }
}
