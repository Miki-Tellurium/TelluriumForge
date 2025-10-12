package com.mikitellurium.telluriumforge.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class BlockRegistrator extends Registrator<Block> {
    private final IForgeRegistry<Item> itemRegistry;

    /**
     * Create a new {@code BlockWithItemRegistrator} using the provided item registry and mod id
     */
    public static BlockRegistrator makeRegistrator(String modId) {
        return new BlockRegistrator(modId);
    }

    protected BlockRegistrator(String modId) {
        super(ForgeRegistries.BLOCKS, modId);
        this.itemRegistry = ForgeRegistries.ITEMS;
    }

    public <S extends Block> S registerWithItem(String id, S block) {
        S blockObj = this.register(id, block);
        registerBlockItem(id, blockObj);
        return blockObj;
    }

    public <S extends Block> ItemBlock registerBlockItem(String id, S block) {
        ItemBlock item = (ItemBlock) new ItemBlock(block).setRegistryName(modLoc(id));
        itemRegistry.register(item);
        return item;
    }
}
