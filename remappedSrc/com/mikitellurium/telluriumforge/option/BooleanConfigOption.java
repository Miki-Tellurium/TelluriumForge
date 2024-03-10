package com.mikitellurium.telluriumforge.option;

import com.mikitellurium.telluriumforge.config.TelluriumConfig;
import com.mikitellurium.telluriumforge.config.ConfigEntry;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

/**
 * A concrete subclass of {@link ConfigOption} representing a boolean configuration option.
 * {@code BooleanConfigOption} is specifically designed to work with {@link ConfigEntry}
 * for boolean values.
 */
public class BooleanConfigOption extends ConfigOption<Boolean> {

    /**
     * The text to be displayed when the boolean option is enabled.
     */
    private final Text enabledText;

    /**
     * The text to be displayed when the boolean option is disabled.
     */
    private final Text disabledText;

    /**
     * Constructs a {@link ConfigOption} for the given {@link ConfigEntry}
     * of type boolean.
     * The {@code Identifier} is used to generate the text and tooltip
     * translation keys for this option.
     *
     * @param identifier   The {@code Identifier} for the configuration option
     * @param enabledKey   The translation key for the enabled text
     * @param disabledKey  The translation key for the disabled text
     * @param config       The boolean {@link ConfigEntry} associated with the option
     */
    public BooleanConfigOption(Identifier identifier, String enabledKey, String disabledKey,
                               ConfigEntry<Boolean> config) {
        super(identifier, config);
        this.enabledText = Text.translatable(this.getTranslationKey() + "." + enabledKey);
        this.disabledText = Text.translatable(this.getTranslationKey() + "." + disabledKey);
    }

    /**
     * Constructs a {@link ConfigOption} for the given {@link ConfigEntry}
     * of type boolean.
     * The {@code Identifier} is used to generate the text and tooltip
     * translation keys for this option.
     * Uses default translation keys for "true" and "false" texts.
     *
     * @param identifier The {@code Identifier} for the configuration option
     * @param config     The boolean {@link ConfigEntry} associated with the option
     */
    public BooleanConfigOption(Identifier identifier, ConfigEntry<Boolean> config) {
        this(identifier, "true", "false", config);
    }

    /**
     * Converts the {@code BooleanConfigOption} to a {@code SimpleOption} representing a UI option for
     * the boolean {@link ConfigEntry}.
     *
     * @return The {@code SimpleOption} for the boolean configuration option
     */
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

