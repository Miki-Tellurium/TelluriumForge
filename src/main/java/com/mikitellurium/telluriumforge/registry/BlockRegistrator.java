package com.mikitellurium.telluriumforge.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
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

    @SuppressWarnings("unchecked")
    @Override
    public <S extends Block> S register(String id, S object) {
        return super.register(id, (S) object.setTranslationKey(id));
    }

    public <S extends Block> S registerWithItem(String id, S block) {
        S blockObj = this.register(id, block);
        this.registerBlockItem(id);
        return blockObj;
    }

    @SuppressWarnings("ConstantConditions")
    public ItemBlock registerBlockItem(String id) {
        ResourceLocation loc = modLoc(id);
        if (!ForgeRegistries.BLOCKS.containsKey(loc)) {
            throw new RuntimeException("Block " + loc + " not registered yet.");
        }
        ItemBlock item = (ItemBlock) new ItemBlock(ForgeRegistries.BLOCKS.getValue(loc)).setRegistryName(modLoc(id)).setTranslationKey(id);
        itemRegistry.register(item);
        return item;
    }
}
