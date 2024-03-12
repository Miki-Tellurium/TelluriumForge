package com.mikitellurium.telluriumforge.config;

import com.mikitellurium.telluriumforge.config.entry.ConfigEntry;
import com.mikitellurium.telluriumforge.config.entry.EnumConfigEntry;
import com.mikitellurium.telluriumforge.config.entry.RangedConfigEntry;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A class used to create and load simple config files.
 */
@SuppressWarnings("rawtypes")
public class TelluriumConfig {

    private static final String fileExtension = ".properties";

    private final Logger logger;
    /**
     * The path of the config file.
     */
    private final String file;
    /**
     * The type of this config file.
     */
    private final Type type;

    /**
     * List of the file comments.
     */
    private final List<String> comments = new ArrayList<>();
    /**
     * List of the entries managed by this file.
     */
    private final List<ConfigEntry> entries = new ArrayList<>();

    /**
     * Constructs a new {@code TelluriumConfig} instance.
     * The instance manages a single config file, to make multiple
     * files use multiple instances.
     * @param fileName the name of the config file
     * @param type the type of the config file
     */
    public TelluriumConfig(String fileName, Type type) {
        this.file = FabricLoader.getInstance().getConfigDir().resolve(fileName + "-" + type.getName() + fileExtension).toString();
        this.type = type;
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
     * Return the type of the config file.
     *
     * @return the config file type
     */
    public Type getType() {
        return type;
    }

    /**
     * Returns the entry corresponding to the specified key
     * or {@code null} if the config entry isn't found.
     *
     * @return the {@link ConfigEntry} corresponding to the key or {@code null} if no entry is found
     */
    public ConfigEntry<?> getEntry(String key) {
        for (ConfigEntry entry : entries) {
            if (entry.getKey().equals(key)) {
                return entry;
            }
        }

        return null;
    }

    /**
     * Gets the list of all entries managed by this
     * {@code TelluriumConfig} instance.
     *
     * @return the list of entries
     */
    public List<ConfigEntry> getEntries() {
        return this.entries;
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
        comments.add(comment);
        return this;
    }

    /**
     * Provides a convenient way to create instances of the {@link EntryBuilder} class.
     * The {@code EntryBuilder} is used for building and configuring entries.
     * <p>
     * Example Usage:
     * <pre>{@code
     * TelluriumConfig newConfigFile = new TelluriumConfig("fileName");
     * EntryBuilder entryBuilder = newConfigFile.entryBuilder();
     * // Use entryBuilder to build and configure entries.
     * }</pre>
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
            if (!comments.isEmpty()) {
                for (String s : comments) {
                    writer.write("# " + s + newline);
                }
            }

            writer.write(newline);
            writer.write("[Settings]" + newline);
            writer.write(newline);

            // Write config entries
            if (!entries.isEmpty()) {
                for (ConfigEntry<?> configEntry : entries) {
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

    /**
     * Loads all values from the config file
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

    /**
     * Reads an entry from the config file and load its value
     */
    @SuppressWarnings("unchecked")
    private void parseConfigEntry(String string, int line) {
        if (isValueLine(string)) {
            String[] entryParts = string.split("=", 2);

            if (entryParts.length == 2) {
                ConfigEntry configEntry = this.getEntry(entryParts[0]);
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

    /**
     * Check if the line currently loaded is an entry or a comment
     */
    private boolean isValueLine(String line) {
        if (line.isEmpty()) return false;
        else if (line.startsWith("#") || line.startsWith("[")) return false;
        return true;
    }

    /**
     * The EntryBuilder class provides a convenient way to construct configuration entries
     * within a specific {@code TelluriumConfig} instance. Configuration entries represent
     * individual settings with associated values, and {@code EntryBuilder} simplifies their creation and
     * configuration.
     * <p>
     * Example Usage:
     * <pre>{@code
     * TelluriumConfig config = new TelluriumConfig();
     * EntryBuilder entryBuilder = config.entryBuilder();
     *
     * // Define a boolean entry with a default value
     * ConfigEntry<Boolean> booleanEntry = entryBuilder.define("enableFeature", true);
     *
     * // Define an integer entry within a specified range
     * RangedConfigEntry<Integer> rangedEntry = entryBuilder.comment("This is a comment")
     *      .defineInRange("cooldownSeconds", 1, 60, 10);
     *
     * // Define an enum entry
     * EnumConfigEntry<SomeEnum> rangedEntry = entryBuilder
     *      .define("enumConfig", SomeEnum.VALUE)
     *      .comment("This is an enum entry");
     *
     * // Call build() to save and load the values during mod initialization
     * config.build();
     * }</pre>
     * It's convenient to save the entries in static fields so their values
     * can be accessed and changed from everywhere in the code using the
     * {@link ConfigEntry#getValue} and {@link ConfigEntry#setValue} methods.
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
         * @return the entry that was created
         */
        public ConfigEntry<Boolean> define(String key, boolean defaultValue) {
            ConfigEntry<Boolean> newEntry = new ConfigEntry<>(parent, key, defaultValue);
            entries.add(newEntry);
            this.buildEntry(newEntry);
            return newEntry;
        }

        /**
         * Makes an entry that holds a integer value.
         *
         * @param key the name of the entry
         * @param defaultValue the default value of the entry
         * @return the entry that was created
         */
        public ConfigEntry<Integer> define(String key, int defaultValue) {
            ConfigEntry<Integer> newEntry = new ConfigEntry<>(parent, key, defaultValue);
            entries.add(newEntry);
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
         * @param defaultValue the default value of the entry
         * @param minValue the minimum value this entry can have
         * @param maxValue the maximum value this entry can have
         * @return the entry that was created
         */
        public RangedConfigEntry<Integer> defineInRange(String key, int defaultValue, int minValue, int maxValue) {
            RangedConfigEntry<Integer> newEntry = new RangedConfigEntry<>(parent, key, defaultValue, minValue, maxValue);
            entries.add(newEntry);
            this.buildEntry(newEntry);
            return newEntry;
        }

        /**
         * Makes an entry that holds a double value.
         *
         * @param key the name of the entry
         * @param defaultValue the default value of the entry
         * @return the entry that was created
         */
        public ConfigEntry<Double> define(String key, double defaultValue) {
            ConfigEntry<Double> newEntry = new ConfigEntry<>(parent, key, defaultValue);
            entries.add(newEntry);
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
         * @param defaultValue the default value of the entry
         * @param minValue the minimum value this entry can have
         * @param maxValue the maximum value this entry can have
         * @return the entry that was created
         */
        public RangedConfigEntry<Double> defineInRange(String key, double defaultValue, double minValue, double maxValue) {
            RangedConfigEntry<Double> newEntry = new RangedConfigEntry<>(parent, key, defaultValue, minValue, maxValue);
            entries.add(newEntry);
            this.buildEntry(newEntry);
            return newEntry;
        }

        /**
         * Makes an entry that holds a long value.
         *
         * @param key the name of the entry
         * @param defaultValue the default value of the entry
         * @return the entry that was created
         */
        public ConfigEntry<Long> define(String key, long defaultValue) {
            ConfigEntry<Long> newEntry = new ConfigEntry<>(parent, key, defaultValue);
            entries.add(newEntry);
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
         * @param defaultValue the default value of the entry
         * @param minValue the minimum value this entry can have
         * @param maxValue the maximum value this entry can have
         * @return the entry that was created
         */
        public RangedConfigEntry<Long> defineInRange(String key, long defaultValue, long minValue, long maxValue) {
            RangedConfigEntry<Long> newEntry = new RangedConfigEntry<>(parent, key, defaultValue, minValue, maxValue);
            entries.add(newEntry);
            this.buildEntry(newEntry);
            return newEntry;
        }

        /**
         * Makes an entry that holds a string value.
         *
         * @param key the name of the entry
         * @param defaultValue the default value of the entry
         * @return the entry that was created
         */
        public ConfigEntry<String> define(String key, String defaultValue) {
            ConfigEntry<String> newEntry = new ConfigEntry<>(parent, key, defaultValue);
            entries.add(newEntry);
            this.buildEntry(newEntry);
            return newEntry;
        }

        /**
         * Makes an entry that holds an enum value.
         *
         * @param key the name of the entry
         * @param defaultValue the default value of the entry
         * @return the entry that was created
         * @param <E> the enum type of this entry
         */
        public <E extends Enum<E>> EnumConfigEntry<E> define(String key, E defaultValue) {
            EnumConfigEntry<E> newEntry = new EnumConfigEntry<>(parent, key, defaultValue);
            entries.add(newEntry);
            this.buildEntry(newEntry);
            return newEntry;
        }

        /**
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

    /**
     * A convenience object that holds the comment context for
     * a {@link ConfigEntry} instance before it is constructed by the
     * {@link EntryBuilder}
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

    public enum Type {
        COMMON("common"),
        CLIENT("client"),
        SERVER("server");

        private final String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
