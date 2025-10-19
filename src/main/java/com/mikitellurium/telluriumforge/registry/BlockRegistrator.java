package com.mikitellurium.telluriumforge.registry;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
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
        this.registerBlockItem(id, block);
        return blockObj;
    }

    @SuppressWarnings("ConstantConditions")
    public ItemBlock registerBlockItem(String id, Block block) {
        ItemBlock item = (ItemBlock) new ItemBlock(block).setRegistryName(modLoc(id)).setTranslationKey(id);
        itemRegistry.register(item);
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(modLoc(id), "inventory"));
        return item;
    }
}
