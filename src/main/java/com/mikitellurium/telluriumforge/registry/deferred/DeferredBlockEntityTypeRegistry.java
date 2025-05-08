package com.mikitellurium.telluriumforge.registry.deferred;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
public class DeferredBlockEntityTypeRegistry extends DeferredRegister<BlockEntityType<?>> {
    protected DeferredBlockEntityTypeRegistry(String namespace) {
        super(Registries.BLOCK_ENTITY_TYPE, namespace);
    }

    public static DeferredBlockEntityTypeRegistry create(String modId) {
        return new DeferredBlockEntityTypeRegistry(modId);
    }

    @Override
    public <I extends BlockEntityType<?>> DeferredBlockEntityType<I> register(String name, Supplier<? extends I> sup) {
        return (DeferredBlockEntityType<I>) super.register(name, sup);
    }

    @Override
    public <I extends BlockEntityType<?>> DeferredBlockEntityType<I> register(String name, Function<ResourceLocation, ? extends I> func) {
        return (DeferredBlockEntityType<I>) super.register(name, func);
    }

    @Override
    protected <I extends BlockEntityType<?>> DeferredBlockEntityType<I> createHolder(ResourceKey<? extends Registry<BlockEntityType<?>>> registryKey, ResourceLocation key) {
        return DeferredBlockEntityType.createBlockEntity(key);
    }
}
