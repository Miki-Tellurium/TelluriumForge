package com.mikitellurium.telluriumforge.option;

import com.mikitellurium.telluriumforge.config.EnumConfigEntry;
import com.mojang.serialization.Codec;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Locale;

/**
 * A concrete subclass of {@link ConfigOption} representing an enum configuration option.
 * {@code EnumConfigOption} is specifically designed to work with {@link EnumConfigEntry}
 * for enum values.
 *
 * @param <E> The type of the enum
 */
public class EnumConfigOption<E extends Enum<E>> extends ConfigOption<E> {

    /**
     * The class representing the enum type.
     */
    private final Class<E> clazz;

    /**
     * Constructs a {@link ConfigOption} for the given {@link EnumConfigEntry}.
     * The {@code Identifier} is used to generate the text and tooltip
     * translation keys for this option.
     *
     * @param identifier The {@code Identifier} for the configuration option
     * @param config     The enum configuration entry associated with the option
     */
    public EnumConfigOption(Identifier identifier, EnumConfigEntry<E> config) {
        super(identifier, config);
        this.clazz = config.getEnumClass();
    }

    /**
     * Overrides the {@link ConfigOption#getConfig()} method to provide a more specific return type.
     *
     * @return The {@link EnumConfigEntry} associated with the option
     */
    @Override
    public EnumConfigEntry<E> getConfig() {
        return (EnumConfigEntry<E>) super.getConfig();
    }

    /**
     * Converts the {@code EnumConfigOption} to a {@code SimpleOption} representing a UI option for the enum configuration.
     *
     * @return The {@code SimpleOption} for the {@code EnumConfigOption}
     */
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

