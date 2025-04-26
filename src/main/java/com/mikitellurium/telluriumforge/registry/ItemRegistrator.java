package com.mikitellurium.telluriumforge.registry;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public record ItemRegistrator(DeferredRegister.Items registry) implements RegistryHelper<Item> {

    @Override
    public <S extends Item> DeferredItem<S> register(String id, Supplier<S> item) {
        return this.registry.register(id, item);
    }

    /**
     * Create a new {@code ItemRegistrator} using the provided mod id
     */
    public static ItemRegistrator makeRegistrator(String modId) {
        return new ItemRegistrator(DeferredRegister.createItems(modId));
    }

}
