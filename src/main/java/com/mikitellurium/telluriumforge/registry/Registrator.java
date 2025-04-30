package com.mikitellurium.telluriumforge.registry;

import net.minecraft.registry.Registry;

import java.util.function.Supplier;

public record Registrator<T>(Registry<T> registry, String modId) implements RegistryHelper<T>, IdentifierProvider {

    @Override
    public <S extends T> S register(String id, Supplier<S> object) {
        return Registry.register(registry, modIdentifier(id), object.get());
    }

}
