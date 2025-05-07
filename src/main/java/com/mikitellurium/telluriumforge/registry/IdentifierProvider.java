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
    default Identifier ofMod(String path) {
        return Identifier.of(this.modId(), path);
    }

    /**
     * Creates an {@link Identifier} using the "minecraft" namespace and the specified path.
     */
    default Identifier ofMc(String path) {
        return Identifier.of(path);
    }

}
