package com.mikitellurium.telluriumforge.config;

import com.mikitellurium.telluriumforge.config.serializer.EntryWriter;

import java.util.List;

/**
 * An object used to save a config value in a
 * config file. To make a new entry see the
 * implementation of {@link TelluriumConfig.EntryBuilder}.
 *
 * @param <T> Is the type of value held by this entry
 */
public interface IConfigEntry<T> {
    /**
     * @return the {@link TelluriumConfig} instance that holds this entry
     */
    TelluriumConfig getParentConfig();

    /**
     * @return the class of this config value
     */
    Class<T> getType();

    /**
     * @return the key of this entry
     */
    String getKey();

    /**
     * @return the default value of this entry
     */
    T getDefault();

    /**
     * @return the current loaded value for this entry
     */
    T get();

    /**
     * Change the currently loaded value of this entry.<br>
     * If this is called during the execution of the game, call
     * {@link TelluriumConfig#save()} before closing the current
     * game instance to save the new value to the config file.
     * @param value the new value
     */
    void set(T value);

    /**
     * Add a comment for this entry.
     * @param comment the comment to write before the entry
     * @return the config entry that was commented
     */
    <E extends IConfigEntry<T>> E comment(String comment);

    /**
     * @return the comments list of this entry
     */
    List<String> getComments();

    /**
     * @return return a string representation of the passed
     * value used for writing the config file
     */
    String writeValue(T value);

    /**
     * Parse the value from the string read from the config file
     */
    T readValue(String string);

    void writeEntry(EntryWriter writer);
}
