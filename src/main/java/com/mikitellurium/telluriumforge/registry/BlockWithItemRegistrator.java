package com.mikitellurium.telluriumforge.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public record BlockWithItemRegistrator(DeferredRegister<Block> registry, DeferredRegister<Item> items) implements RegistryHelper<Block> {

    @Override
    public RegistryObject<Block> register(String id, Supplier<Block> object) {
        return registry.register(id, object);
    }

    public RegistryObject<Block> registerWithItem(String id, Supplier<Block> object) {
        return this.registerWithItem(id, object, new Item.Properties());
    }

    public RegistryObject<Block> registerWithItem(String id, Supplier<Block> object, Item.Properties itemProperties) {
        RegistryObject<Block> blockObj = registry.register(id, object);
        items.register(id, () -> new BlockItem(blockObj.get(), itemProperties));
        return blockObj;
    }

    public void registerWithItem(IEventBus eventBus) {
        if (eventBus == MinecraftForge.EVENT_BUS) {
            throw new RuntimeException("Trying to register to the Forge event bus. Use the mod bus instead.");
        }
        registry.register(eventBus);
    }

    public static BlockWithItemRegistrator makeRegistrator(RegistryHelper<Item> items, String modId) {
        return new BlockWithItemRegistrator(DeferredRegister.create(ForgeRegistries.BLOCKS, modId), items.registry());
    }

}
