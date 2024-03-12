package com.mikitellurium.telluriumforge.config.entry;

import com.mikitellurium.telluriumforge.config.TelluriumConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * An object used to save a config value in a
 * config file. To make a new entry see the
 * implementation of {@link TelluriumConfig.EntryBuilder}.
 *
 * @param <T> Is the type of value held by this entry
 */
@SuppressWarnings("fieldCanBeLocal")
public class ConfigEntry<T> {

    private final TelluriumConfig builder;
    private final List<String> comments = new ArrayList<>();
    private final String key;
    private final T defaultValue;
    private T value;

    protected ConfigEntry(TelluriumConfig parent, String key, T defaultValue) {
        this.builder = parent;
        this.key = key;
        this.defaultValue = defaultValue;
    }

    /**
     * @return the {@link TelluriumConfig} instance that holds this entry
     */
    public TelluriumConfig getParentConfig() {
        return builder;
    }

    /**
     * @return the key of this entry
     */
    public String getKey() {
        return key;
    }

    /**
     * @return the default value of this entry
     */
    public T getDefaultValue() {
        return defaultValue;
    }

    /**
     * @return the current loaded value for this entry
     */
    public T getValue() {
        if (value == null || value.toString().isBlank()) {
            return defaultValue;
        }

        return value;
    }

    /**
     * Change the currently loaded value of this entry.
     * <p>
     * If this is called during the execution of the game, call
     * {@link TelluriumConfig#save()} before the game close to save the
     * new value to the config file.
     * @param value the new value
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * Add a comment for this entry.
     * @param comment the comment to write before the entry
     * @return the config entry that was commented
     */
    public ConfigEntry<T> comment(String comment) {
        this.comments.add(comment);
        return this;
    }

    /**
     * @return the comments list of this entry
     */
    public List<String> getComments() {
        return comments;
    }

}
