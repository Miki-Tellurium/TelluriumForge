package com.mikitellurium.telluriumforge.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
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

/**
 * The {@code RegistryHelper} class provides utility methods for registering various game elements
 * such as blocks, items, block entities, etc.
 */
public class RegistryHelper {

    /**
     * The mod id associated with this {@code RegistryHelper}.
     */
    private final String modId;

    /**
     * Constructs a new {@code RegistryHelper} with the specified mod id.
     * The mod id will be used as the {@code Identifier} namespace.
     *
     * @param modId The mod identifier
     */
    public RegistryHelper(String modId) {
        this.modId = modId;
    }

    /**
     * Registers a block with the game registry.
     *
     * @param id  The id of the block
     * @param block The block to register
     * @return The registered block
     */
    public Block registerBlock(String id, Block block) {
        return Registry.register(Registries.BLOCK, getIdentifier(id), block);
    }

    /**
     * Registers a block with its corresponding item in the game registry.
     *
     * @param id  The id of the block
     * @param block The block to register
     * @return The registered block
     */
    public Block registerBlockWithItem(String id, Block block) {
        Block blockToReturn = registerBlock(id, block);
        registerItem(id, new BlockItem(blockToReturn, new FabricItemSettings()));
        return blockToReturn;
    }

    /**
     * Registers an item with the game registry.
     *
     * @param id The id of the item
     * @param item The item to register
     * @return The registered item
     */
    public Item registerItem(String id, Item item) {
        return Registry.register(Registries.ITEM, getIdentifier(id), item);
    }

    /**
     * Registers a block entity with the game registry.
     *
     * @param id        The id of the block entity
     * @param blockEntity The block entity to register
     * @return The registered block entity type
     */
    public <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(String id, BlockEntityType<T> blockEntity) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, getIdentifier(id), blockEntity);
    }

    /**
     * Registers an item group with the game registry.
     *
     * @param id      The id of the item group
     * @param itemGroup The item group to register
     * @return The registered item group
     */
    public ItemGroup registerItemGroup(String id, ItemGroup itemGroup) {
        return Registry.register(Registries.ITEM_GROUP, getIdentifier(id), itemGroup);
    }

    /**
     * Registers an entity type with the game registry.
     *
     * @param id   The id of the entity type
     * @param entity The entity type to register
     * @return The registered entity type
     */
    public <T extends Entity> EntityType<T> registerEntity(String id, EntityType<T> entity) {
        return Registry.register(Registries.ENTITY_TYPE, getIdentifier(id), entity);
    }

    /**
     * Registers an enchantment with the game registry.
     *
     * @param id       The id of the enchantment
     * @param enchantment The enchantment to register
     * @return The registered enchantment
     */
    public Enchantment registerEnchantment(String id, Enchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT, getIdentifier(id), enchantment);
    }

    /**
     * Registers a tag key with the game registry.
     *
     * @param <T>        The type of the tag
     * @param <R>        The type of the registry
     * @param tagRegistry The registry key for the tag
     * @param id        The id of the tag
     * @return The registered tag key
     */
    public <T, R extends Registry<T>> TagKey<T> registerTag(RegistryKey<R> tagRegistry, String id) {
        return TagKey.of(tagRegistry, getIdentifier(id));
    }

    /**
     * Registers a screen handler type with the game registry.
     *
     * @param <H>     The type of the screen handler
     * @param <S>     The type of the handled screen
     * @param <T>     The type of the screen handler type
     * @param id    The id of the screen handler
     * @param handler The screen handler type to register
     * @param screen  The provider for the handled screen
     * @return The registered screen handler type
     */
    public <H extends ScreenHandler, S extends HandledScreen<H>, T extends ScreenHandlerType<H>>
    ScreenHandlerType<H> registerScreen(String id, T handler, HandledScreens.Provider<H, S> screen) {
        ScreenHandlerType<H> handlerTypeToReturn = Registry.register(Registries.SCREEN_HANDLER, getIdentifier(id), handler);
        HandledScreens.register(handlerTypeToReturn, screen);
        return handlerTypeToReturn;
    }

    /**
     * Gets the mod id associated with this {@code RegistryHelper}.
     *
     * @return The mod id
     */
    public String getModId() {
        return modId;
    }

    /**
     * Creates an {@code Identifier} using the mod id and the specified id.
     *
     * @param id The id for the {@code Identifier}
     * @return The created {@code Identifier}
     */
    public Identifier getIdentifier(String id) {
        return new Identifier(modId, id);
    }

}

