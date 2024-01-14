package com.mikitellurium.telluriumforge.option;

import com.mikitellurium.telluriumforge.config.TelluriumConfig;
import com.mojang.serialization.Codec;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Locale;

/**
 * A concrete subclass of ConfigOption representing an enumeration configuration option.
 * EnumConfigOption is specifically designed to work with {@link TelluriumConfig.EnumConfigEntry}
 * for enumeration values.
 *
 * @param <E> The type of the enumeration.
 */
public class EnumConfigOption<E extends Enum<E>> extends ConfigOption<E> {

    /**
     * The class representing the enumeration type.
     */
    private final Class<E> clazz;

    /**
     * Constructs a ConfigOption for the given {@link TelluriumConfig.EnumConfigEntry}.
     * The Identifier is used to generate the text and tooltip
     * translation keys for this option.
     *
     * @param identifier The identifier for the configuration option
     * @param config     The enumeration configuration entry associated with the option
     */
    public EnumConfigOption(Identifier identifier, TelluriumConfig.EnumConfigEntry<E> config) {
        super(identifier, config);
        this.clazz = config.getEnumClass();
    }

    /**
     * Overrides the getConfig method to provide a more specific return type.
     *
     * @return The enumeration configuration entry associated with the option
     */
    @Override
    public TelluriumConfig.EnumConfigEntry<E> getConfig() {
        return (TelluriumConfig.EnumConfigEntry<E>) super.getConfig();
    }

    /**
     * Converts the EnumConfigOption to a SimpleOption representing a UI option for the enumeration configuration.
     *
     * @return The SimpleOption for the enumeration configuration option
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

