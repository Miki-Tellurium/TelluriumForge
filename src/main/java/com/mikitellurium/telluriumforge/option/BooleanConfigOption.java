package com.mikitellurium.telluriumforge.option;

import com.mikitellurium.telluriumforge.config.TelluriumConfig;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BooleanConfigOption extends ConfigOption<Boolean> {

    private final Text enabledText;
    private final Text disabledText;

    public BooleanConfigOption(Identifier identifier, String enabledKey, String disabledKey,
                               TelluriumConfig.ConfigEntry<Boolean> config) {
        super(identifier, config);
        this.enabledText = Text.translatable(this.getTranslationKey() + "." + enabledKey);
        this.disabledText = Text.translatable(this.getTranslationKey() + "." + disabledKey);
    }

    public BooleanConfigOption(Identifier identifier, TelluriumConfig.ConfigEntry<Boolean> config) {
        this(identifier, "true", "false", config);
    }

    @Override
    public SimpleOption<Boolean> asOption() {
        if (enabledText != null && disabledText != null) {
            return new SimpleOption<>(this.getTranslationKey(), this.getTooltip(),
                    (text, value) -> value ? enabledText : disabledText,
                    SimpleOption.BOOLEAN,
                    this.getDefaultValue(), this.changeCallback());
        }
        return SimpleOption.ofBoolean(this.getTranslationKey(), this.getDefaultValue(), this.changeCallback());
    }

}
