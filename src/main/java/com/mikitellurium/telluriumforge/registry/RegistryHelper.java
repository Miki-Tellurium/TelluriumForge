package com.mikitellurium.telluriumforge.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
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

/**
 * The {@code RegistryHelper} class provides utility methods for registering various game elements
 * such as blocks, items, block entities, etc.
 */
public class RegistryHelper implements IdentifierProvider {

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

    @Override
    public String modId() {
        return this.modId;
    }

    /**
     * Initializes the given {@link InitializedRegistry} using the provided {@link RegistryHelper}.
     *
     * @param helper   the {@link RegistryHelper} used for initialization
     * @param registry the {@link InitializedRegistry} to initialize
     */
    public static void initRegistry(RegistryHelper helper, InitializedRegistry registry) {
        registry.init(helper);
    }

    /**
     * Registers a block with the game registry.
     *
     * @param path  The path of the block
     * @param block The block to register
     * @return The registered block
     */
    public Block registerBlock(String path, Block block) {
        return Registry.register(Registries.BLOCK, modIdentifier(path), block);
    }

    /**
     * Registers a block with its corresponding item in the game registry.
     *
     * @param path  The path of the block
     * @param block The block to register
     * @return The registered block
     */
    public Block registerBlockWithItem(String path, Block block) {
        Block blockToReturn = registerBlock(path, block);
        registerItem(path, new BlockItem(blockToReturn, new FabricItemSettings()));
        return blockToReturn;
    }

    /**
     * Registers an item with the game registry.
     *
     * @param path The path of the item
     * @param item The item to register
     * @return The registered item
     */
    public Item registerItem(String path, Item item) {
        return Registry.register(Registries.ITEM, modIdentifier(path), item);
    }

    /**
     * Registers a block entity with the game registry.
     *
     * @param path The path of the block entity
     * @param blockEntity The block entity to register
     * @return The registered block entity type
     */
    public <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(String path, BlockEntityType<T> blockEntity) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, modIdentifier(path), blockEntity);
    }

    /**
     * Registers an item group with the game registry.
     *
     * @param path      The path of the item group
     * @param itemGroup The item group to register
     * @return The registered item group
     */
    public ItemGroup registerItemGroup(String path, ItemGroup itemGroup) {
        return Registry.register(Registries.ITEM_GROUP, modIdentifier(path), itemGroup);
    }

    /**
     * Registers an entity type with the game registry.
     *
     * @param path   The path of the entity type
     * @param entity The entity type to register
     * @return The registered entity type
     */
    public <T extends Entity> EntityType<T> registerEntity(String path, EntityType<T> entity) {
        return Registry.register(Registries.ENTITY_TYPE, modIdentifier(path), entity);
    }

    /**
     * Registers an enchantment with the game registry.
     *
     * @param path       The path of the enchantment
     * @param enchantment The enchantment to register
     * @return The registered enchantment
     */
    public Enchantment registerEnchantment(String path, Enchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT, modIdentifier(path), enchantment);
    }

    /**
     * Registers a tag key with the game registry.
     *
     * @param <T>        The type of the tag
     * @param <R>        The type of the registry
     * @param tagRegistry The registry key for the tag
     * @param path        The path of the tag
     * @return The registered tag key
     */
    public <T, R extends Registry<T>> TagKey<T> registerTag(RegistryKey<R> tagRegistry, String path) {
        return TagKey.of(tagRegistry, modIdentifier(path));
    }

    /**
     * Registers a screen handler type with the game registry.
     *
     * @param <H>     The type of the screen handler
     * @param <T>     The type of the screen handler type
     * @param path    The path of the screen handler
     * @param handler The screen handler type to register
     * @return The registered screen handler type
     */
    public <H extends ScreenHandler, T extends ScreenHandlerType<H>> ScreenHandlerType<H> registerScreenHandler(String path, T handler) {
        return Registry.register(Registries.SCREEN_HANDLER, modIdentifier(path), handler);
    }

    /**
     * Registers a handled screen for a given screen handler type.
     *
     * @param <H>     the type of {@link ScreenHandler} associated with the screen
     * @param <S>     the type of {@link HandledScreen} associated with the screen handler
     * @param type    the {@link ScreenHandlerType} that defines the type of screen handler
     * @param screen  the {@link HandledScreens.Provider} that provides the corresponding handled screen
     */
    public <H extends ScreenHandler, S extends HandledScreen<H>> void registerHandledScreen(ScreenHandlerType<H> type, HandledScreens.Provider<H, S> screen) {
        HandledScreens.register(type, screen);
    }

}

