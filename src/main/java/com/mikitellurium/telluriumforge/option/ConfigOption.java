package com.mikitellurium.telluriumforge.option;

import com.mikitellurium.telluriumforge.config.TelluriumConfig;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

/**
 * A base abstract class representing a configuration option.
 * Concrete subclasses are expected to provide specific
 * implementations for creating a UI option for the configuration.
 *
 * @param <T> The type of the configuration value.
 */
public abstract class ConfigOption<T> {

    /**
     * The translation key for the configuration option.
     */
    private final String translationKey;

    /**
     * The translation key for the tooltip associated with the configuration option.
     */
    private final String tooltipKey;

    /**
     * The configuration entry associated with the option.
     */
    private final TelluriumConfig.ConfigEntry<T> config;

    /**
     * Constructs a ConfigOption for the given {@link TelluriumConfig.ConfigEntry}.
     * The Identifier is used to generate the text and tooltip
     * translation keys for this option.
     *
     * @param identifier The identifier for the configuration option
     * @param config     The configuration entry associated with the option
     */
    public ConfigOption(Identifier identifier, TelluriumConfig.ConfigEntry<T> config) {
        this.translationKey = identifier.toTranslationKey("option");
        this.config = config;
        this.tooltipKey = this.translationKey + ".tooltip";
    }

    /**
     * Gets the translation key for the name of the configuration option.
     *
     * @return The translation key
     */
    public String getTranslationKey() {
        return translationKey;
    }

    /**
     * Gets the translation key for the tooltip associated with the configuration option.
     *
     * @return The tooltip translation key
     */
    public String getTooltipKey() {
        return tooltipKey;
    }

    /**
     * Gets the {@link TelluriumConfig.ConfigEntry} associated with the option.
     *
     * @return The configuration entry
     */
    public TelluriumConfig.ConfigEntry<T> getConfig() {
        return config;
    }

    /**
     * Gets the default value for the configuration option.
     *
     * @return The default value
     */
    public T getDefaultValue() {
        return this.config.getValue();
    }

    /**
     * Gets the tooltip factory for creating the tooltip associated with the configuration option.
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
     * Gets the change callback for handling changes to the configuration value.
     *
     * @return The change callback
     */
    protected Consumer<T> changeCallback() {
        return this.config::setValue;
    }

    /**
     * Converts the ConfigOption to a SimpleOption representing a UI option for the configuration.
     *
     * @return The SimpleOption for the configuration option
     */
    public abstract SimpleOption<T> asOption();

}

