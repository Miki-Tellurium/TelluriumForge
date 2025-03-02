package com.mikitellurium.telluriumforge.registry;

import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public interface RegistryHelper<T> {

    <S extends T> RegistryObject<S> register(String id, Supplier<S> object);

    DeferredRegister<T> registry();

    default void register() {
        this.registry().register(FMLJavaModLoadingContext.get().getModEventBus());
    }

}
