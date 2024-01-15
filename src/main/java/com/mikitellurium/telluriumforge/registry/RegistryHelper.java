package com.mikitellurium.telluriumforge.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class RegistryHelper {

    private final String modId;

    public RegistryHelper(String modId) {
        this.modId = modId;
    }

    public Block registerBlock(String name, Block block) {
        return Registry.register(Registries.BLOCK, getIdentifier(name), block);
    }

    public Block registerBlockWithItem(String name, Block block) {
        Block blockToReturn = registerBlock(name, block);
        registerItem(name, new BlockItem(blockToReturn, new FabricItemSettings()));
        return blockToReturn;
    }

    public Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, getIdentifier(name), item);
    }

    public <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(String name, BlockEntityType<T> blockEntity) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, getIdentifier(name), blockEntity);
    }

    public <T, R extends Registry<T>> TagKey<T> registerTag(RegistryKey<R> tagRegistry, String name) {
        return TagKey.of(tagRegistry, getIdentifier(name));
    }

    public ItemGroup registerItemGroup(String name, ItemGroup itemGroup) {
        return Registry.register(Registries.ITEM_GROUP, getIdentifier(name), itemGroup);
    }

    public <H extends ScreenHandler, S extends HandledScreen<H>, T extends ScreenHandlerType<H>>
    ScreenHandlerType<H> registerScreen(String name, T handler, HandledScreens.Provider<H, S> screen) {
        ScreenHandlerType<H> handlerTypeToReturn = Registry.register(Registries.SCREEN_HANDLER, getIdentifier(name), handler);
        HandledScreens.register(handlerTypeToReturn, screen);
        return handlerTypeToReturn;
    }

    public String getModId() {
        return modId;
    }

    public Identifier getIdentifier(String id) {
        return new Identifier(modId, id);
    }

}
