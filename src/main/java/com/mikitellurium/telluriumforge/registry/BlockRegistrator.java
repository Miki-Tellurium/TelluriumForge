package com.mikitellurium.telluriumforge.registry;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.Supplier;

public record BlockRegistrator(String modId) implements RegistryHelper<Block>, IdentifierProvider {

    @Override
    public <T extends Block> T register(String id, Supplier<T> block) {
        return Registry.register(Registries.BLOCK, ofMod(id), block.get());
    }

    public <T extends Block> T registerWithItem(String id, Supplier<T> block) {
        return this.registerWithItem(id, block, new Item.Settings());
    }

    public <T extends Block> T registerWithItem(String id, Supplier<T> block, Item.Settings itemSettings) {
        T blockObj = Registry.register(Registries.BLOCK, ofMod(id), block.get());
        registerBlockItem(id, blockObj, itemSettings);
        return blockObj;
    }

    public <T extends Block> BlockItem registerBlockItem(String id, T block, Item.Settings itemSettings) {
        return Registry.register(Registries.ITEM, ofMod(id), new BlockItem(block, itemSettings));
    }

}
