package com.mikitellurium.telluriumforge.option;

import com.mikitellurium.telluriumforge.config.RangedConfigEntry;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.util.Identifier;

/**
 * A subclass of {@link ConfigOption} representing an integer configuration option.
 */
public class IntConfigOption extends ConfigOption<Integer> {

    /**
     * Constructs a {@link ConfigOption} for the given {@link RangedConfigEntry}.
     * The {@code Identifier} is used to generate the text and tooltip
     * translation keys for this option.
     */
    public IntConfigOption(Identifier identifier, RangedConfigEntry<Integer> config) {
        super(identifier, config);
    }

    @Override
    public RangedConfigEntry<Integer> getConfig() {
        return (RangedConfigEntry<Integer>) super.getConfig();
    }

    @Override
    public SimpleOption<Integer> asOption() {
        return new SimpleOption<>(this.getTranslationKey(), this.getTooltip(),
                GameOptions::getGenericValueText,
                new SimpleOption.ValidatingIntSliderCallbacks(this.getConfig().getMinValue(), this.getConfig().getMaxValue()),
                this.getDefaultValue(), this.changeCallback());
    }

}

