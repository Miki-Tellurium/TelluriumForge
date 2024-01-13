package com.mikitellurium.telluriumforge.config;

import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * You can use and modify this freely if you want, just respect the License
 * and credit me.
 *
 * MIT License
 *
 * Copyright (c) 2023 Miki_Tellurium
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

/**
 * A class used to create and load simple config files.
 * @author Miki Tellurium
 * @version 1.5.0-Fabric
 */
@SuppressWarnings("rawtypes")
public class TelluriumConfig {

    private static final String fileExtension = ".properties";

    private final Logger logger;
    private final String file; // The path of the config file

    private final List<String> COMMENTS = new ArrayList<>();
    private final List<ConfigEntry> ENTRIES = new ArrayList<>();

    /**
     * TelluriumConfig object constructor.
     * @param fileName the name of the config file
     */
    public TelluriumConfig(String fileName) {
        this.file = FabricLoader.getInstance().getConfigDir().resolve(fileName + fileExtension).toString();
        this.logger = LoggerFactory.getLogger(fileName);
    }

    /**
     * Return the path of the config file.
     *
     * @return the config file path
     */
    public String getConfigFilePath() {
        return file;
    }

    /**
     * Add a comment to the config file.
     * <p>
     * Comments will be written at the top of the file before
     * any entry.
     *
     * @param comment the comment to write to the config file
     * @return the builder object
     */
    public TelluriumConfig comment(String comment) {
        COMMENTS.add(comment);
        return this;
    }

    /**
     * Provides a convenient way to create instances of the EntryBuilder class.
     * The EntryBuilder class is used for building and configuring entries.
     * <p>
     * Example Usage:
     * <pre><code>
     * TelluriumConfig newConfigFile = new TelluriumConfig("fileName");
     * EntryBuilder entryBuilder = newConfigFile.entryBuilder();
     * // Use entryBuilder to build and configure entries.
     * </code></pre>
     *
     * @return A new instance of EntryBuilder
     * @see EntryBuilder
     */
    public EntryBuilder entryBuilder() {
        return new EntryBuilder(this);
    }

    /**
     * Build the config file.
     * <p>
     * If the file already exist also load all its entries values.
     * This should be called during the initialization phase of the game.
     */
    public void build() {
        File file = new File(this.file);
        if (file.exists()) {
            load();
        }
        save();
    }

    /**
     * Saves the current loaded values to the config file.
     * <p>
     * This is automatically called from the {@link TelluriumConfig#build()} method
     * but can also be called individually to save values when they
     * are changed during the execution of the game.
     */
    public void save() {
        try {
            FileWriter writer = new FileWriter(file);
            final String newline = System.lineSeparator(); // Wrap text

            // Write comments
            if (!COMMENTS.isEmpty()) {
                for (String s : COMMENTS) {
                    writer.write("#" + s + newline);
                }
            }

            writer.write(newline);
            writer.write("[Settings]" + newline);
            writer.write(newline);

            // Write config entries
            if (!ENTRIES.isEmpty()) {
                for (ConfigEntry configEntry : ENTRIES) {
                    String entrySeparator = "=";

                    List<String> list = configEntry.getComments();
                    if (!list.isEmpty()) {
                        for (String s : list) {
                            writer.write("# " + s + newline);
                        }
                    }

                    if (configEntry instanceof RangedConfigEntry<?> rangedEntry) {
                        writer.write("# Range: min=" + rangedEntry.getMinValue() +
                                ", max=" + rangedEntry.getMaxValue() + newline);
                    } else if (configEntry instanceof EnumConfigEntry<?> enumEntry) {
                        writer.write("# Options: ");
                        Enum<?>[] constants = enumEntry.getEnumClass().getEnumConstants();
                        for (Enum<?> constant : constants) {
                            writer.write(constant.toString());
                            if (!constants[constants.length - 1].equals(constant)) {
                                writer.write(", ");
                            }
                        }
                        writer.write(newline);
                    }

                    writer.write("# Default = " + configEntry.getDefaultValue() + newline);
                    writer.write(configEntry.getKey() + entrySeparator + configEntry.getValue() + newline);
                    writer.write(newline);
                }
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            logger.error("Something went wrong when trying to write config file \"" + this.getConfigFilePath() + "\"");
            e.printStackTrace();
        }
    }

    /*
     * Loads values from the config file
     */
    private void load() {
        try {
            File file = new File(this.file);
            Scanner reader = new Scanner(file);
            for (int line = 1; reader.hasNextLine(); line++) {
                parseConfigEntry(reader.nextLine(), line);
            }
        } catch (IOException e) {
            logger.error("Something went wrong when trying to read config file \"" + this.getConfigFilePath() + "\"");
            e.printStackTrace();
        }
    }

    /*
     * Reads an entry from the config file and load its value
     */
    @SuppressWarnings("unchecked")
    private void parseConfigEntry(String string, int line) {
        if (isValueLine(string)) {
            String[] entryParts = string.split("=", 2);

            if (entryParts.length == 2) {
                ConfigEntry configEntry = this.getConfigEntry(entryParts[0]);
                String valueString = entryParts[1];

                if (configEntry != null) {

                    try {
                        if (configEntry instanceof EnumConfigEntry<?> enumEntry) {
                            enumEntry.setValueFromString(valueString);
                            return;
                        }
                        Class<?> valueType = configEntry.getValue().getClass();
                        switch (valueType.getSimpleName()) {
                            case "Boolean" -> configEntry.setValue(Boolean.parseBoolean(valueString));
                            case "Integer" -> configEntry.setValue(Integer.parseInt(valueString));
                            case "Double" -> configEntry.setValue(Double.parseDouble(valueString));
                            case "Long" -> configEntry.setValue(Long.parseLong(valueString));
                            case "String" -> configEntry.setValue(String.valueOf(valueString));
                            default -> { // Handle unsupported types
                                configEntry.setValue(configEntry.getDefaultValue());
                                logger.error("Unsupported value type for entry \"" + configEntry.getKey() + "\". Loaded default value.");
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        configEntry.setValue(configEntry.getDefaultValue());
                        logger.error("Invalid value for entry \"" + configEntry.getKey() + "\". Loaded default value.");
                    }

                } else {
                    logger.error("Unknown entry found: \"" + entryParts[0] + "\" in config file \"" + this.getConfigFilePath() + "\" at line " + line + ". Removing it.");
                }

            } else {
                logger.error("Unknown entry found: \"" + entryParts[0] + "\" in config file \"" + this.getConfigFilePath() + "\" at line " + line + ". Removing it.");
            }
        }
    }

    /*
     * Returns the config entry corresponding to the specified key
     * or null if the config entry wasn't found
     */
    private ConfigEntry<?> getConfigEntry(String key) {
        for (ConfigEntry entry : ENTRIES) {
            if (entry.getKey().equals(key)) {
                return entry;
            }
        }

        return null;
    }

    /*
     * Check if the current line we are reading is an entry or a comment
     */
    private boolean isValueLine(String line) {
        if (line.isEmpty()) return false;
        else if (line.startsWith("#") || line.startsWith("[")) return false;
        return true;
    }

    /**
     * The EntryBuilder class provides a convenient way to construct configuration entries
     * within a specific TelluriumConfig instance. Configuration entries represent
     * individual settings with associated values, and EntryBuilder simplifies their creation and
     * configuration.
     * <p>
     * Example Usage:
     * <pre><code>
     * TelluriumConfig telluriumConfig = new TelluriumConfig();
     * EntryBuilder entryBuilder = telluriumConfig.entryBuilder();
     *
     * // Define a boolean entry with a default value
     *{@literal ConfigEntry<Boolean>} booleanEntry = entryBuilder.define("enableFeature", true);
     *
     * // Define an integer entry within a specified range
     *{@literal RangedConfigEntry<Integer>} rangedEntry = entryBuilder.comment("This is a comment")
     * .defineInRange("cooldownSeconds", 1, 60, 10);
     * </code></pre>
     *
     */
    public class EntryBuilder {

        private final TelluriumConfig parent;
        private EntryBuilderContext context = new EntryBuilderContext();

        private EntryBuilder(TelluriumConfig parent) {
            this.parent = parent;
        }


        /**
         * Add a comment to the entry.
         *
         * @param comment the comment to add to the entry
         * @return this instance of the entry builder
         */
        public EntryBuilder comment(String comment) {
            context.add(comment);
            return this;
        }

        /**
         * Makes an entry that holds a boolean value.
         *
         * @param key the name of the entry
         * @param defaultValue the default value of the entry
         * @return the config entry that was created
         */
        public ConfigEntry<Boolean> define(String key, boolean defaultValue) {
            ConfigEntry<Boolean> newEntry = new ConfigEntry<>(parent, key, defaultValue);
            ENTRIES.add(newEntry);
            this.buildEntry(newEntry);
            return newEntry;
        }

        /**
         * Makes an entry that holds a integer value.
         *
         * @param key the name of the entry
         * @param defaultValue the default value of the entry
         * @return the config entry that was created
         */
        public ConfigEntry<Integer> define(String key, int defaultValue) {
            ConfigEntry<Integer> newEntry = new ConfigEntry<>(parent, key, defaultValue);
            ENTRIES.add(newEntry);
            this.buildEntry(newEntry);
            return newEntry;
        }

        /**
         * Makes an entry that holds a integer value.
         * <p>
         * This value will always stay between the specified
         * range (inclusive).
         *
         * @param key the name of the entry
         * @param minValue the minimum value this entry can have
         * @param maxValue the maximum value this entry can have
         * @param defaultValue the default value of the entry
         * @return the config entry that was created
         */
        public RangedConfigEntry<Integer> defineInRange(String key, int minValue, int maxValue, int defaultValue) {
            RangedConfigEntry<Integer> newEntry = new RangedConfigEntry<>(parent, key, minValue, maxValue, defaultValue);
            ENTRIES.add(newEntry);
            this.buildEntry(newEntry);
            return newEntry;
        }

        /**
         * Makes an entry that holds a double value.
         *
         * @param key the name of the entry
         * @param defaultValue the default value of the entry
         * @return the config entry that was created
         */
        public ConfigEntry<Double> define(String key, double defaultValue) {
            ConfigEntry<Double> newEntry = new ConfigEntry<>(parent, key, defaultValue);
            ENTRIES.add(newEntry);
            this.buildEntry(newEntry);
            return newEntry;
        }

        /**
         * Makes an entry that holds a double value.
         * <p>
         * This value will always stay between the specified
         * range (inclusive).
         *
         * @param key the name of the entry
         * @param minValue the minimum value this entry can have
         * @param maxValue the maximum value this entry can have
         * @param defaultValue the default value of the entry
         * @return the config entry that was created
         */
        public RangedConfigEntry<Double> defineInRange(String key, double minValue, double maxValue, double defaultValue) {
            RangedConfigEntry<Double> newEntry = new RangedConfigEntry<>(parent, key, minValue, maxValue, defaultValue);
            ENTRIES.add(newEntry);
            this.buildEntry(newEntry);
            return newEntry;
        }

        /**
         * Makes an entry that holds a long value.
         *
         * @param key the name of the entry
         * @param defaultValue the default value of the entry
         * @return the config entry that was created
         */
        public ConfigEntry<Long> define(String key, long defaultValue) {
            ConfigEntry<Long> newEntry = new ConfigEntry<>(parent, key, defaultValue);
            ENTRIES.add(newEntry);
            this.buildEntry(newEntry);
            return newEntry;
        }

        /**
         * Makes an entry that holds a long value.
         * <p>
         * This value will always stay between the specified
         * range (inclusive).
         *
         * @param key the name of the entry
         * @param minValue the minimum value this entry can have
         * @param maxValue the maximum value this entry can have
         * @param defaultValue the default value of the entry
         * @return the config entry that was created
         */
        public RangedConfigEntry<Long> defineInRange(String key, long minValue, long maxValue, long defaultValue) {
            RangedConfigEntry<Long> newEntry = new RangedConfigEntry<>(parent, key, minValue, maxValue, defaultValue);
            ENTRIES.add(newEntry);
            this.buildEntry(newEntry);
            return newEntry;
        }

        /**
         * Makes an entry that holds a string value.
         *
         * @param key the name of the entry
         * @param defaultValue the default value of the entry
         * @return the config entry that was created
         */
        public ConfigEntry<String> define(String key, String defaultValue) {
            ConfigEntry<String> newEntry = new ConfigEntry<>(parent, key, defaultValue);
            ENTRIES.add(newEntry);
            this.buildEntry(newEntry);
            return newEntry;
        }

        // Testing
        public <T extends Enum<T>> EnumConfigEntry<T> define(String key, T defaultValue) {
            EnumConfigEntry<T> newEntry = new EnumConfigEntry<>(parent, key, defaultValue);
            ENTRIES.add(newEntry);
            this.buildEntry(newEntry);
            return newEntry;
        }

        /*
        * Build and return the entry then reset the context
        */
        private <T extends ConfigEntry<?>> void buildEntry(T configEntry) {
            List<String> comments = context.getComments();
            if (!comments.isEmpty()) {
                for (String s : context.getComments()) {
                    configEntry.comment(s);
                }
            }
            this.context = new EntryBuilderContext();
        }

    }

    /*
     * A convenience object that holds the comment context for
     * a ConfigEntry instance before it is constructed by the
     * EntryBuilder
     */
    private static class EntryBuilderContext {

        private final List<String> comments = new ArrayList<>();

        private EntryBuilderContext() {}

        public void add(String comment) {
            this.comments.add(comment);
        }

        public List<String> getComments() {
            return comments;
        }

    }

    /**
     * An object used to save a config value in a
     * config file. To make a new entry see the
     * implementation of {@link EntryBuilder}.
     *
     * @param <T> Is the type of value held by this entry
     */
    @SuppressWarnings("fieldCanBeLocal")
    public static class ConfigEntry<T> {

        private final TelluriumConfig builder;
        private final List<String> comments = new ArrayList<>();
        private final String key;
        private final T defaultValue;
        private T value;

        private ConfigEntry(TelluriumConfig parent, String key, T defaultValue) {
            this.builder = parent;
            this.key = key;
            this.defaultValue = defaultValue;
        }

        /**
         * @return the config instance that holds this entry
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

    /**
     * An object used to save a config value that has to be
     * in a certain range. To make a new entry see the
     * implementation of {@link EntryBuilder}.
     *
     * @param <N> Is the type of {@link Number} held by this entry
     */
    public static class RangedConfigEntry<N extends Number> extends ConfigEntry<N> {

        private final N minValue;
        private final N maxValue;

        private RangedConfigEntry(TelluriumConfig parent, String key, N minValue, N maxValue, N defaultValue) {
            super(parent, key, defaultValue);
            this.minValue = minValue;
            this.maxValue = maxValue;
        }

        /**
         * @return the minimum value this entry can have
         */
        public N getMinValue() {
            return minValue;
        }

        /**
         * @return the maximum value this entry can have
         */
        public N getMaxValue() {
            return maxValue;
        }

        /**
         * Change the currently loaded value of this entry.
         * <p>
         * If the new value is out of the specified range for this entry
         * it's automatically set to the closest value inside the range.
         * <p>
         * If this is called during the execution of the game, use
         * {@link TelluriumConfig#save()} before the game close to save the
         * new value to the config file.
         * @param value the new value
         */
        @Override
        public void setValue(N value) {
            if (compare(value, minValue) < 0) {
                super.setValue(minValue);
            } else if (compare(value, maxValue) > 0){
                super.setValue(maxValue);
            } else {
                super.setValue(value);
            }
        }

        /**
         * Compares two values of type T.
         *
         * @param value1 the first value to compare
         * @param value2 the second value to compare
         * @return a negative integer if value1 is less than value2,
         *         zero if they are equal, or a positive integer if
         *         value1 is greater than value2
         */
        @SuppressWarnings("unchecked")
        private int compare(N value1, N value2) {
            return ((Comparable<N>) value1).compareTo(value2);
        }

        /**
         * Add a comment for this entry.
         * @param comment the comment to write before the entry
         * @return the config entry that was commented
         */
        @Override
        public RangedConfigEntry<N> comment(String comment) {
            super.comment(comment);
            return this;
        }

    }

    /**
     * An object used to save a config value that use
     * an enum. To make a new entry see the
     * implementation of {@link EntryBuilder}.
     *
     * @param <E> The enum type associated with this entry
     */
    public static class EnumConfigEntry<E extends Enum<E>> extends ConfigEntry<E> {

        private EnumConfigEntry(TelluriumConfig parent, String key, E defaultValue) {
            super(parent, key, defaultValue);
        }

        public Class<E> getEnumClass() {
            return this.getDefaultValue().getDeclaringClass();
        }

        /**
         * Sets the value of the enum configuration entry based
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
         * @param comment the comment to write before the entry
         * @return the config entry that was commented
         */
        @Override
        public EnumConfigEntry<E> comment(String comment) {
            super.comment(comment);
            return this;
        }

    }

}
