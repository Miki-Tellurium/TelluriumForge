package com.mikitellurium.telluriumforge.test;

import com.mikitellurium.telluriumforge.registry.InitializedRegistry;
import com.mikitellurium.telluriumforge.registry.RegistryHelper;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class ExampleRegistry implements InitializedRegistry {

    public static Item EXAMPLE_ITEM;
    public static Block EXAMPLE_BLOCK;

    @Override
    public void init(RegistryHelper helper) {
        EXAMPLE_ITEM = helper.registerItem("example_item", new Item(new Item.Settings()));
        EXAMPLE_BLOCK = helper.registerBlock("example_block", new Block(AbstractBlock.Settings.create()));
    }

}
