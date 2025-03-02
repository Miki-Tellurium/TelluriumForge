package com.mikitellurium.telluriumforge.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public record BlockWithItemRegistrator(DeferredRegister<Block> registry, DeferredRegister<Item> items) implements RegistryHelper<Block> {

    @Override
    public <S extends Block> RegistryObject<S> register(String id, Supplier<S> block) {
        return registry.register(id, block);
    }

    public <S extends Block> RegistryObject<S> registerWithItem(String id, Supplier<S> block) {
        return this.registerWithItem(id, block, new Item.Properties());
    }

    public <S extends Block> RegistryObject<S> registerWithItem(String id, Supplier<S> block, Item.Properties itemProperties) {
        RegistryObject<S> blockObj = registry.register(id, block);
        items.register(id, () -> new BlockItem(blockObj.get(), itemProperties));
        return blockObj;
    }

    public static BlockWithItemRegistrator makeRegistrator(RegistryHelper<Item> items, String modId) {
        return new BlockWithItemRegistrator(DeferredRegister.create(ForgeRegistries.BLOCKS, modId), items.registry());
    }

}
