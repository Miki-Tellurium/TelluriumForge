package com.mikitellurium.telluriumforge.option;

import com.mikitellurium.telluriumforge.config.IConfigEntry;
import com.mikitellurium.telluriumforge.config.NumberConfigEntry;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

/**
 * A base abstract class representing a configuration option.
 * Subclasses are expected to provide specific implementations
 * for creating a UI option for the {@link NumberConfigEntry}.
 */
public abstract class ConfigOption<T> {

    private final String translationKey;
    private final String tooltipKey;
    private final IConfigEntry<T> config;

    /**
     * Constructs a {@code ConfigOption} for the given {@link NumberConfigEntry}.
     * The {@code Identifier} is used to generate the text and tooltip
     * translation keys for this option.
     */
    public ConfigOption(Identifier identifier, IConfigEntry<T> config) {
        this.translationKey = identifier.toTranslationKey("option");
        this.config = config;
        this.tooltipKey = this.translationKey + ".tooltip";
    }

    /**
     * @return the translation key
     */
    public String getTranslationKey() {
        return translationKey;
    }

    /**
     * @return the tooltip translation key
     */
    public String getTooltipKey() {
        return tooltipKey;
    }

    /**
     * @return the {@link IConfigEntry}
     */
    public IConfigEntry<T> getConfig() {
        return config;
    }

    /**
     * @return the default value
     */
    public T getDefaultValue() {
        return this.config.get();
    }

    /**
     * Gets the tooltip factory for creating the tooltip associated with the {@link ConfigOption}.
     * The default implementation returns a translation if one is set for the tooltip key or an
     * empty tooltip if a translation is not present.
     */
    protected SimpleOption.TooltipFactory<T> getTooltip() {
        Text tooltip = Text.translatable(tooltipKey);
        return tooltip.contains(Text.of(tooltipKey)) ? SimpleOption.emptyTooltip() : SimpleOption.constantTooltip(tooltip);
    }

    /**
     * @return the change callback for handling changes to the {@link IConfigEntry} value
     */
    protected Consumer<T> changeCallback() {
        return this.config::set;
    }

    /**
     * Converts the {@code ConfigOption} to a {@code SimpleOption} representing a UI option for the configuration.
     *
     * @return the {@code SimpleOption} for the configuration option
     */
    public abstract SimpleOption<T> asOption();

}

