package com.mikitellurium.telluriumforge.config.entry;

import com.mikitellurium.telluriumforge.config.TelluriumConfig;

/**
 * An object used to save a config value that use
 * an enum. To make a new entry see the
 * implementation of {@link TelluriumConfig.EntryBuilder}.
 *
 * @param <E> The enum type associated with this entry
 */
public class EnumConfigEntry<E extends Enum<E>> extends ConfigEntry<E> {

    protected EnumConfigEntry(TelluriumConfig parent, String key, E defaultValue) {
        super(parent, key, defaultValue);
    }

    public Class<E> getEnumClass() {
        return this.getDefaultValue().getDeclaringClass();
    }

    /**
     * Sets the value of the {@code EnumConfigEntry} based
     * on the provided string. The string should match the
     * name of one of the enum constants.
     *
     * @param text The string representation of the enum value
     */
    public void setValueFromString(String text) {
        E value = E.valueOf(this.getEnumClass(), text);
        this.setValue(value);
    }

    /**
     * Add a comment for this entry.
     * Overrides the {@link ConfigEntry#comment} method to provide a more specific
     * return type.
     * @param comment the comment to write before the entry
     * @return the config entry that was commented
     */
    @Override
    public EnumConfigEntry<E> comment(String comment) {
        super.comment(comment);
        return this;
    }

}