package com.mikitellurium.telluriumforge.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public record Registrator<T>(DeferredRegister<T> registry) implements RegistryHelper<T> {

    @Override
    public RegistryObject<T> register(String id, Supplier<T> supplier) {
        return registry.register(id, supplier);
    }

    public static <T> RegistryHelper<T> makeRegistrator(ResourceKey<Registry<T>> resourceKey, String modId) {
        return new Registrator<>(DeferredRegister.create(resourceKey, modId));
    }

    public static <T> RegistryHelper<T> makeRegistrator(IForgeRegistry<T> registry, String modId) {
        return makeRegistrator(registry.getRegistryKey(), modId);
    }

}
