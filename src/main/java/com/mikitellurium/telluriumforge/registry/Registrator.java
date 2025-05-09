package com.mikitellurium.telluriumforge.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public record Registrator<T>(DeferredRegister<T> registry) implements RegistryHelper<T> {
    @Override
    public <S extends T> DeferredHolder<T, S> register(String id, Supplier<S> object) {
        return registry.register(id, object);
    }

    /**
     * Create a new registrator using the provided {@link ResourceKey} and mod id
     */
    public static <T> Registrator<T> makeRegistrator(ResourceKey<Registry<T>> resourceKey, String modId) {
        return new Registrator<>(DeferredRegister.create(resourceKey, modId));
    }
}
