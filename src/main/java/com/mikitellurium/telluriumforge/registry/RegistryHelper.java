package com.mikitellurium.telluriumforge.registry;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public interface RegistryHelper<T> {

    RegistryObject<T> register(String id, Supplier<T> object);

    DeferredRegister<T> registry();

    default void register() {
//        if (eventBus == MinecraftForge.EVENT_BUS) {
//            throw new RuntimeException("Trying to register to the Forge event bus. Use the mod bus instead.");
//        }
        this.registry().register(FMLJavaModLoadingContext.get().getModEventBus());
    }

}
