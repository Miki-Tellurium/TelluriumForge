package com.mikitellurium.telluriumforge.option;

import com.mikitellurium.telluriumforge.config.TelluriumConfig;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public abstract class ConfigOption<T> {

    // todo: add docs for config options

    private final String translationKey;
    private final String tooltipKey;
    private final TelluriumConfig.ConfigEntry<T> config;

    public ConfigOption(Identifier identifier, TelluriumConfig.ConfigEntry<T> config) {
        this.translationKey = identifier.toTranslationKey("option");
        this.config = config;
        this.tooltipKey = this.translationKey + ".tooltip";
    }

    public String getTranslationKey() {
        return translationKey;
    }

    public String getTooltipKey() {
        return tooltipKey;
    }

    public TelluriumConfig.ConfigEntry<T> getConfig() {
        return config;
    }

    public T getDefaultValue() {
        return this.config.getValue();
    }

    protected SimpleOption.TooltipFactory<T> getTooltip() {
        Text tooltip = Text.translatable(tooltipKey);
        return tooltip.contains(Text.of(tooltipKey)) ? SimpleOption.emptyTooltip() : SimpleOption.constantTooltip(tooltip);
    }

    protected Consumer<T> changeCallback() {
        return this.config::setValue;
    }

    public abstract SimpleOption<T> asOption();

}
