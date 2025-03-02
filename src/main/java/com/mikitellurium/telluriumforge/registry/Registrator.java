package com.mikitellurium.telluriumforge.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public record Registrator<T>(DeferredRegister<T> registry) implements RegistryHelper<T> {

    @Override
    public <S extends T> RegistryObject<S> register(String id, Supplier<S> object) {
        return registry.register(id, object);
    }

    public static <T> RegistryHelper<T> makeRegistrator(ResourceKey<Registry<T>> resourceKey, String modId) {
        return new Registrator<>(DeferredRegister.create(resourceKey, modId));
    }

    public static <T> RegistryHelper<T> makeRegistrator(IForgeRegistry<T> registry, String modId) {
        return makeRegistrator(registry.getRegistryKey(), modId);
    }

}
