package com.mikitellurium.telluriumforge.option;

import com.mikitellurium.telluriumforge.config.EnumConfigEntry;
import com.mojang.serialization.Codec;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Locale;

/**
 * A subclass of {@link ConfigOption} representing an enum configuration option.
 */
public class EnumConfigOption<E extends Enum<E>> extends ConfigOption<E> {

    private final Class<E> clazz;

    /**
     * Constructs a {@link ConfigOption} for the given {@link EnumConfigEntry}.
     * The {@code Identifier} is used to generate the text and tooltip
     * translation keys for this option.
     */
    public EnumConfigOption(Identifier identifier, EnumConfigEntry<E> config) {
        super(identifier, config);
        this.clazz = config.getType();
    }

    @Override
    public EnumConfigEntry<E> getConfig() {
        return (EnumConfigEntry<E>) super.getConfig();
    }

    @Override
    public SimpleOption<E> asOption() {
        return new SimpleOption<>(this.getTranslationKey(), this.getTooltip(),
                (optionText, value) -> Text.literal(value.name().toUpperCase(Locale.ROOT)),
                new SimpleOption.PotentialValuesBasedCallbacks<>(Arrays.asList(clazz.getEnumConstants()),
                        Codec.STRING.xmap(
                                string -> Arrays.stream(clazz.getEnumConstants())
                                        .filter(e -> e.name().toLowerCase().equals(string)).findAny().orElse(null),
                                newValue -> newValue.name().toLowerCase()
                        )),
                this.getDefaultValue(), this.changeCallback());
    }

}

