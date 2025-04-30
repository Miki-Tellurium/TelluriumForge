package com.mikitellurium.telluriumforge.registry;

import net.minecraft.util.Identifier;

/**
 * A base interface used to make registration helpers.
 */
public interface IdentifierProvider {

    /**
     * Gets the mod id.
     */
    String modId();

    /**
     * Creates an {@link Identifier} using the mod id and the specified path.
     */
    default Identifier modIdentifier(String path) {
        return new Identifier(this.modId(), path);
    }

    /**
     * Creates an {@link Identifier} using the "minecraft" namespace and the specified path.
     */
    default Identifier mcIdentifier(String path) {
        return new Identifier(path);
    }

}
