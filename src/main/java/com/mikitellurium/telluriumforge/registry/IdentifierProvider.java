package com.mikitellurium.telluriumforge.registry;

import net.minecraft.resources.ResourceLocation;

/**
 * A base interface used to make registration helpers.
 */
public interface IdentifierProvider {

    /**
     * Gets the mod id.
     *
     * @return The mod id
     */
    String modId();

    /**
     * Creates an {@code Identifier} using the mod id and the specified path.
     *
     * @param path The path for the {@code Identifier}
     * @return The created {@code Identifier}
     */
    default ResourceLocation modLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(this.modId(), path);
    }

    /**
     * Creates an {@code Identifier} using the "minecraft" namespace and the specified path.
     *
     * @param path The path for the {@code Identifier}
     * @return The created {@code Identifier}
     */
    default ResourceLocation mcLoc(String path) {
        return ResourceLocation.withDefaultNamespace(path);
    }

}
