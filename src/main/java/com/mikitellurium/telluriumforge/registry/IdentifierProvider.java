package com.mikitellurium.telluriumforge.registry;

import net.minecraft.util.ResourceLocation;

/**
 * A base interface used to make registration helpers.
 */
public interface IdentifierProvider {

    String modId();

    /**
     * Creates an {@code Identifier} using the mod id and the specified path.
     *
     * @param path The path for the {@code Identifier}
     * @return The created {@code Identifier}
     */
    default ResourceLocation modLoc(String path) {
        return new ResourceLocation(this.modId(), path);
    }

    /**
     * Creates an {@code Identifier} using the "minecraft" namespace and the specified path.
     *
     * @param path The path for the {@code Identifier}
     * @return The created {@code Identifier}
     */
    default ResourceLocation mcLoc(String path) {
        return new ResourceLocation(path);
    }

}
