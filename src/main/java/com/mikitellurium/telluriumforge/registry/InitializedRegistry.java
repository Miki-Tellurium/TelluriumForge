package com.mikitellurium.telluriumforge.registry;

public interface InitializedRegistry {

    /**
     * Used to initialize this registry static objects using the provided {@link RegistryHelper}.
     * <p>
     * Example:
     * <pre>{@code
     * public static Item EXAMPLE_ITEM;
     * public static Block EXAMPLE_BLOCK;
     *
     * @Override
     * public void init(RegistryHelper helper) {
     *     EXAMPLE_ITEM = helper.registerItem("example_item", new Item(new Item.Settings()));
     *     EXAMPLE_BLOCK = helper.registerBlock("example_block", new Block(AbstractBlock.Settings.create()));
     * }
     * }</pre>
     * <p>
     * Ensure that {@link RegistryHelper#initRegistry} is called during
     * mod initialization to fully initialize the registry with this instance:
     * <pre>{@code
     * // During mod initialization
     * @Override
     * public void onInitialize() {
     *     RegistryHelper.initRegistry(registryHelper, new ExampleRegistry());
     * }
     * }</pre>
     * </p>
     *
     * @param registryHelper the {@link RegistryHelper} instance used to perform the initialization
     */
    void init(RegistryHelper registryHelper);

}
