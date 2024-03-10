package com.mikitellurium.telluriumforge.option;

import com.mikitellurium.telluriumforge.config.RangedConfigEntry;
import com.mikitellurium.telluriumforge.config.TelluriumConfig;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.util.Identifier;

/**
 * A concrete subclass of {@link ConfigOption} representing an integer configuration option.
 * {@code IntConfigOption} is specifically designed to work with {@link RangedConfigEntry}
 * for integer values.
 */
public class IntConfigOption extends ConfigOption<Integer> {

    /**
     * Constructs a {@link ConfigOption} for the given {@link RangedConfigEntry}.
     * The {@code Identifier} is used to generate the text and tooltip
     * translation keys for this option.
     *
     * @param identifier The {@code Identifier} for the configuration option
     * @param config     The {@link RangedConfigEntry} associated with the option
     */
    public IntConfigOption(Identifier identifier, RangedConfigEntry<Integer> config) {
        super(identifier, config);
    }

    /**
     * Overrides the {@link ConfigOption#getConfig()} method to provide a more specific return type.
     *
     * @return The {@link RangedConfigEntry} associated with the option
     */
    @Override
    public RangedConfigEntry<Integer> getConfig() {
        return (RangedConfigEntry<Integer>) super.getConfig();
    }

    /**
     * Converts the {@code IntConfigOption} to a {@code SimpleOption} representing a UI option for the integer configuration.
     *
     * @return The {@code SimpleOption} for the {@code IntConfigOption}
     */
    @Override
    public SimpleOption<Integer> asOption() {
        return new SimpleOption<>(this.getTranslationKey(), this.getTooltip(),
                GameOptions::getGenericValueText,
                new SimpleOption.ValidatingIntSliderCallbacks(this.getConfig().getMinValue(), this.getConfig().getMaxValue()),
                this.getDefaultValue(), this.changeCallback());
    }

}

