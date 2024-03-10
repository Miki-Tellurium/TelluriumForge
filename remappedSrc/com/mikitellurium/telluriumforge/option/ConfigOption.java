package com.mikitellurium.telluriumforge.option;

import com.mikitellurium.telluriumforge.config.ConfigEntry;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

/**
 * A base abstract class representing a configuration option.
 * Concrete subclasses are expected to provide specific
 * implementations for creating a UI option for the {@link ConfigEntry}.
 *
 * @param <T> The type of the configuration value
 */
public abstract class ConfigOption<T> {

    /**
     * The translation key for the {@code ConfigOption} name.
     */
    private final String translationKey;

    /**
     * The translation key for the tooltip associated with the {@code ConfigOption}.
     */
    private final String tooltipKey;

    /**
     * The {@link ConfigEntry} associated with the {@code ConfigOption}.
     */
    private final ConfigEntry<T> config;

    /**
     * Constructs a {@code ConfigOption} for the given {@link ConfigEntry}.
     * The {@code Identifier} is used to generate the text and tooltip
     * translation keys for this option.
     *
     * @param identifier The {@code Identifier} for the configuration option
     * @param config     The configuration entry associated with the option
     */
    public ConfigOption(Identifier identifier, ConfigEntry<T> config) {
        this.translationKey = identifier.toTranslationKey("option");
        this.config = config;
        this.tooltipKey = this.translationKey + ".tooltip";
    }

    /**
     * Gets the translation key for the name of the {@code ConfigOption}.
     *
     * @return The translation key
     */
    public String getTranslationKey() {
        return translationKey;
    }

    /**
     * Gets the translation key for the tooltip associated with the {@code ConfigOption}.
     *
     * @return The tooltip translation key
     */
    public String getTooltipKey() {
        return tooltipKey;
    }

    /**
     * Gets the {@link ConfigEntry} associated with the {@code ConfigOption}.
     *
     * @return The {@link ConfigEntry}
     */
    public ConfigEntry<T> getConfig() {
        return config;
    }

    /**
     * Gets the default value for the {@code ConfigOption}.
     *
     * @return The default value
     */
    public T getDefaultValue() {
        return this.config.getValue();
    }

    /**
     * Gets the tooltip factory for creating the tooltip associated with the {@code ConfigOption}.
     * The default implementation returns a translation if one is set for the tooltip key or an
     * empty tooltip if a translation is not present.
     *
     * @return The tooltip factory
     */
    protected SimpleOption.TooltipFactory<T> getTooltip() {
        Text tooltip = Text.translatable(tooltipKey);
        return tooltip.contains(Text.of(tooltipKey)) ? SimpleOption.emptyTooltip() : SimpleOption.constantTooltip(tooltip);
    }

    /**
     * Gets the change callback for handling changes to the {@link ConfigEntry} value.
     *
     * @return The change callback
     */
    protected Consumer<T> changeCallback() {
        return this.config::setValue;
    }

    /**
     * Converts the {@code ConfigOption} to a {@code SimpleOption} representing a UI option for the configuration.
     *
     * @return The {@code SimpleOption} for the configuration option
     */
    public abstract SimpleOption<T> asOption();

}

