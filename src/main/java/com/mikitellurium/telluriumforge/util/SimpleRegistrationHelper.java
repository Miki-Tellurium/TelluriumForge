package com.mikitellurium.telluriumforge.util;

import net.minecraft.util.Identifier;

/**
 * A base class used to make registration helpers.
 */
public class SimpleRegistrationHelper {

    /**
     * The mod id associated with this {@code SimpleRegistrationHelper}.
     */
    private final String modId;

    /**
     * Constructs a new helper with the specified mod id.
     * The mod id will be used as the {@code Identifier} namespace.
     *
     * @param modId The mod identifier
     */
    public SimpleRegistrationHelper(String modId) {
        this.modId = modId;
    }

    /**
     * Gets the mod id associated with this {@code SimpleRegistrationHelper}.
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
