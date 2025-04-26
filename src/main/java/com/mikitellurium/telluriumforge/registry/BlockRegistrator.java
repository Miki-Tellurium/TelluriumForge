package com.mikitellurium.telluriumforge.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public record BlockRegistrator(DeferredRegister.Blocks registry, DeferredRegister.Items items) implements RegistryHelper<Block> {

    @Override
    public <S extends Block> DeferredBlock<S> register(String id, Supplier<S> block) {
        return registry.register(id, block);
    }

    public <S extends Block> DeferredBlock<S> registerWithItem(String id, Supplier<S> block) {
        return this.registerWithItem(id, block, new Item.Properties());
    }

    public <S extends Block> DeferredBlock<S> registerWithItem(String id, Supplier<S> block, Item.Properties itemProperties) {
        DeferredBlock<S> blockObj = registry.register(id, block);
        registerBlockItem(id, blockObj, itemProperties);
        return blockObj;
    }

    public <S extends Block> DeferredItem<BlockItem> registerBlockItem(String id, DeferredBlock<S> block) {
        return this.registerBlockItem(id, block, new Item.Properties());
    }

    public <S extends Block> DeferredItem<BlockItem> registerBlockItem(String id, DeferredBlock<S> block, Item.Properties itemProperties) {
        return items.register(id, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    /**
     * Create a new {@code BlockRegistrator} using the provided item registry and mod id
     */
    public static BlockRegistrator makeRegistrator(RegistryHelper<Item> items, String modId) {
        return new BlockRegistrator(DeferredRegister.createBlocks(modId), (DeferredRegister.Items) items.registry());
    }

}
