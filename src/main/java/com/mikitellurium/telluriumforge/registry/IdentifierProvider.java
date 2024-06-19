package com.mikitellurium.telluriumforge.registry;

import net.minecraft.util.Identifier;

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
    default Identifier modIdentifier(String path) {
        return Identifier.of(this.modId(), path);
    }

    /**
     * Creates an {@code Identifier} using the "minecraft" namespace and the specified path.
     *
     * @param path The path for the {@code Identifier}
     * @return The created {@code Identifier}
     */
    default Identifier mcIdentifier(String path) {
        return Identifier.ofVanilla(path);
    }

}
