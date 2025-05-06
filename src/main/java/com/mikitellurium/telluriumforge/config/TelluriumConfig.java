package com.mikitellurium.telluriumforge.config;

import com.mikitellurium.telluriumforge.config.serializer.EntryWriterImpl;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A class used to create and load simple config files.
 */
public final class TelluriumConfig {

    private static final String fileExtension = ".properties";

    private final Logger logger;
    private final String file;
    private final Type type;
    private final List<String> comments = new ArrayList<>();
    private final List<IConfigEntry<?>> entries = new ArrayList<>();

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
     * @return the config file path
     */
    public String getConfigFilePath() {
        return file;
    }

    /**
     * @return the config file type
     */
    public Type getType() {
        return type;
    }

    /**
     * @return the {@link IConfigEntry} corresponding to the key or {@code null} if no entry is found
     */
    public IConfigEntry<?> getEntry(String key) {
        for (IConfigEntry<?> entry : entries) {
            if (entry.getKey().equals(key)) {
                return entry;
            }
        }
        return null;
    }

    /**
     * @return the list of entries managed by this config
     */
    public List<IConfigEntry<?>> getEntries() {
        return this.entries;
    }

    /**
     * Add a comment to the config file.<br>
     * Comments will be written at the top of the file before
     * any entry.
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
            this.load();
        }
        this.save();
    }

    /**
     * Saves the current loaded values to the config file.
     * <p>
     * This is automatically called from the {@link TelluriumConfig#build()} method
     * but can also be called to save values changed during game execution.
     */
    public void save() {
        try {
            FileWriter writer = new FileWriter(file);
            final String newline = System.lineSeparator();

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
            EntryWriterImpl entryWriter = new EntryWriterImpl(writer, this.getConfigFilePath(), logger);
            if (!entries.isEmpty()) {
                for (IConfigEntry<?> configEntry : entries) {
                    configEntry.writeEntry(entryWriter);
                    writer.write(newline);
                }
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            logger.error("Something went wrong when trying to write config file \"{}\"", this.getConfigFilePath(), e);
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
                this.parseConfigEntry(reader.nextLine(), line);
            }
        } catch (IOException e) {
            logger.error("Something went wrong when trying to read config file \"{}\"", this.getConfigFilePath(), e);
        }
    }

    /**
     * Reads an entry from the config file and load its value
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private void parseConfigEntry(String string, int line) {
        if (!isValueLine(string)) return;

        String[] entryParts = string.split("=", 2);
        if (entryParts.length != 2) {
            logger.error("Unknown entry found: \"{}\" in config file \"{}\" at line {}.", entryParts[0], this.getConfigFilePath(), line);
            return;
        }
        IConfigEntry configEntry = this.getEntry(entryParts[0]);
        String valueString = entryParts[1];
        if (configEntry == null) {
            logger.error("Unknown entry found: \"{}\" in config file \"{}\" at line {}.", entryParts[0], this.getConfigFilePath(), line);
            return;
        }
        try {
            configEntry.set(configEntry.readValue(valueString));
        } catch (IllegalArgumentException | ClassCastException e) {
            configEntry.set(configEntry.getDefault());
            logger.error("Unsupported value type for entry \"{}\". Loaded default value.", configEntry.getKey(), e);
        }
    }

    /**
     * Check if the line is an entry
     */
    private boolean isValueLine(String line) {
        if (line.isEmpty()) {
            return false;
        }
        return !line.startsWith("#") && !line.startsWith("[");
    }

    /**
     * Allows to create config entries for a specific {@link TelluriumConfig} instance.
     * Config entries hold a default and a configurable value.
     * <p>
     * Example usage:
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
     * {@link IConfigEntry#get} and {@link IConfigEntry#set} methods.
     */
    public class EntryBuilder {
        private final TelluriumConfig parent;
        private final EntryBuilderContext context = new EntryBuilderContext();

        private EntryBuilder(TelluriumConfig parent) {
            this.parent = parent;
        }

        /**
         * Add a comment to the entry.
         */
        public EntryBuilder comment(String comment) {
            context.add(comment);
            return this;
        }

        /**
         * Makes an entry that holds a boolean value.
         */
        public BooleanConfigEntry define(String key, boolean defaultValue) {
            BooleanConfigEntry newEntry = new BooleanConfigEntry(parent, key, defaultValue);
            entries.add(newEntry);
            this.buildEntry(newEntry);
            return newEntry;
        }

        /**
         * Makes an entry that holds a integer value.
         */
        public NumberConfigEntry<Integer> define(String key, int defaultValue) {
            NumberConfigEntry<Integer> newEntry = new NumberConfigEntry<>(parent, Integer.class, key, defaultValue);
            entries.add(newEntry);
            this.buildEntry(newEntry);
            return newEntry;
        }

        /**
         * Makes an entry that holds a integer value.<br>
         * This value will always stay between the specified
         * range (inclusive).
         */
        public RangedConfigEntry<Integer> defineInRange(String key, int defaultValue, int minValue, int maxValue) {
            RangedConfigEntry<Integer> newEntry = new RangedConfigEntry<>(parent, Integer.class, key, defaultValue, minValue, maxValue);
            entries.add(newEntry);
            this.buildEntry(newEntry);
            return newEntry;
        }

        /**
         * Makes an entry that holds a double value.
         */
        public NumberConfigEntry<Double> define(String key, double defaultValue) {
            NumberConfigEntry<Double> newEntry = new NumberConfigEntry<>(parent, Double.class, key, defaultValue);
            entries.add(newEntry);
            this.buildEntry(newEntry);
            return newEntry;
        }

        /**
         * Makes an entry that holds a double value.<br>
         * This value will always stay between the specified
         * range (inclusive).
         */
        public RangedConfigEntry<Double> defineInRange(String key, double defaultValue, double minValue, double maxValue) {
            RangedConfigEntry<Double> newEntry = new RangedConfigEntry<>(parent, Double.class, key, defaultValue, minValue, maxValue);
            entries.add(newEntry);
            this.buildEntry(newEntry);
            return newEntry;
        }

        /**
         * Makes an entry that holds a long value.
         */
        public NumberConfigEntry<Long> define(String key, long defaultValue) {
            NumberConfigEntry<Long> newEntry = new NumberConfigEntry<>(parent, Long.class, key, defaultValue);
            entries.add(newEntry);
            this.buildEntry(newEntry);
            return newEntry;
        }

        /**
         * Makes an entry that holds a long value.<br>
         * This value will always stay between the specified
         * range (inclusive).
         */
        public RangedConfigEntry<Long> defineInRange(String key, long defaultValue, long minValue, long maxValue) {
            RangedConfigEntry<Long> newEntry = new RangedConfigEntry<>(parent, Long.class, key, defaultValue, minValue, maxValue);
            entries.add(newEntry);
            this.buildEntry(newEntry);
            return newEntry;
        }

        /**
         * Makes an entry that holds a string value.
         */
        public StringConfigEntry define(String key, String defaultValue) {
            StringConfigEntry newEntry = new StringConfigEntry(parent, key, defaultValue);
            entries.add(newEntry);
            this.buildEntry(newEntry);
            return newEntry;
        }

        /**
         * Makes an entry that holds an enum value.
         */
        public <E extends Enum<E>> EnumConfigEntry<E> define(String key, E defaultValue) {
            EnumConfigEntry<E> newEntry = new EnumConfigEntry<>(parent, defaultValue.getDeclaringClass(), key, defaultValue);
            entries.add(newEntry);
            this.buildEntry(newEntry);
            return newEntry;
        }

        /**
        * Build and return the entry then reset the context
        */
        private <T extends IConfigEntry<?>> void buildEntry(T configEntry) {
            List<String> comments = context.getComments();
            if (!comments.isEmpty()) {
                for (String s : context.getComments()) {
                    configEntry.comment(s);
                }
            }
            this.context.clear();
        }

    }

    /**
     * Holds the comment context for a {@link IConfigEntry} instance before
     * it is constructed by the {@link EntryBuilder}.
     */
    private static class EntryBuilderContext {
        private final List<String> comments = new ArrayList<>();

        private EntryBuilderContext() {}

        private void add(String comment) {
            this.comments.add(comment);
        }

        private List<String> getComments() {
            return comments;
        }

        private void clear() {
            this.comments.clear();
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
