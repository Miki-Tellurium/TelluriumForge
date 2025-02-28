package com.mikitellurium.telluriumforge.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public interface InitializedBlockRegistry extends InitializedRegistry<Block> {

    @Override
    default void init(DeferredRegister<Block> deferredRegister) {
        throw new RuntimeException("Use the other init() method that allows item registration.");
    }

    void init(DeferredRegister<Block> blockRegister, DeferredRegister<Item> itemRegister);

    default RegistryObject<Block> registerBlockWithItem(String id, Supplier<Block> block, DeferredRegister<Block> blockRegister, DeferredRegister<Item> itemRegister) {
        return this.registerBlockWithItem(id, block, new Item.Properties(), blockRegister, itemRegister);
    }

    default RegistryObject<Block> registerBlockWithItem(String id, Supplier<Block> block, Item.Properties itemProperties, DeferredRegister<Block> blockRegister, DeferredRegister<Item> itemRegister) {
        RegistryObject<Block> blockObj = blockRegister.register(id, block);
        itemRegister.register(id, () -> new BlockItem(blockObj.get(), itemProperties));
        return blockObj;
    }

}
