package com.mikitellurium.telluriumforge.option;

import com.mikitellurium.telluriumforge.config.TelluriumConfig;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class IntConfigOption extends ConfigOption<Integer>{

    public IntConfigOption(Identifier identifier, TelluriumConfig.RangedConfigEntry<Integer> config) {
        super(identifier, config);
    }

    @Override
    public TelluriumConfig.RangedConfigEntry<Integer> getConfig() {
        return (TelluriumConfig.RangedConfigEntry<Integer>) super.getConfig();
    }

    @Override
    public SimpleOption<Integer> asOption() {
        return new SimpleOption<>(this.getTranslationKey(), this.getTooltip(),
                GameOptions::getGenericValueText,
                new SimpleOption.ValidatingIntSliderCallbacks(this.getConfig().getMinValue(), this.getConfig().getMaxValue()),
                this.getDefaultValue(), this.changeCallback());
    }

}
