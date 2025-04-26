package com.mikitellurium.telluriumforge.registry;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Objects;
import java.util.function.Supplier;

public interface RegistryHelper<T> {

    <S extends T> DeferredHolder<T, S> register(String id, Supplier<S> object);

    DeferredRegister<T> registry();

    default void register() {
        IEventBus modEventBus = Objects.requireNonNull(ModLoadingContext.get().getActiveContainer().getEventBus());
        this.registry().register(modEventBus);
    }

}
