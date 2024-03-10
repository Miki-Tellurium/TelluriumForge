package com.mikitellurium.telluriumforge.registry;

import net.minecraft.util.Identifier;

/**
 * A base interface used to make registration helpers.
 */
public interface SimpleRegistrationHelper {

    /**
     * Gets the mod id.
     *
     * @return The mod id
     */
    String getModId();

    /**
     * Creates an {@code Identifier} using the mod id and the specified id.
     *
     * @param id The id for the {@code Identifier}
     * @return The created {@code Identifier}
     */
    default Identifier getIdentifier(String id) {
        return new Identifier(this.getModId(), id);
    }

}
