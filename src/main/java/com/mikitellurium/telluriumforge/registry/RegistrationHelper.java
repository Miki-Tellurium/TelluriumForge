package com.mikitellurium.telluriumforge.registry;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public record RegistrationHelper<T>(DeferredRegister<T> registry) {

    public RegistryObject<T> register(String id, Supplier<T> supplier) {
        return registry.register(id, supplier);
    }

    public void register(IEventBus eventBus) {
        if (eventBus == MinecraftForge.EVENT_BUS) {
            throw new RuntimeException("Trying to register to the Forge event bus. Use the mod bus instead.");
        }
        registry.register(eventBus);
    }

}
