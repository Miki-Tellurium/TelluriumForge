package com.mikitellurium.telluriumforge.registry;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

public class RegistryHelper {

    private final IEventBus modEventBus;
    private final List<DeferredRegister<?>> registers = new ArrayList<>();

    public RegistryHelper(IEventBus modEventBus) {
        this.modEventBus = modEventBus;
    }

    public <T> RegistryHelper addRegistry(DeferredRegister<T> register, InitializedRegistry<T> registry) {
        registry.init(register);
        registers.add(register);
        return this;
    }

    public RegistryHelper addBlockWithItemRegistry(DeferredRegister<Block> blockRegister, DeferredRegister<Item> itemRegister, InitializedBlockRegistry registry) {
        registry.init(blockRegister, itemRegister);
        registers.add(blockRegister);
        registers.add(itemRegister);
        return this;
    }

    public void registerAll() {
        registers.forEach((r) -> r.register(modEventBus));
        registers.clear();
    }

}

