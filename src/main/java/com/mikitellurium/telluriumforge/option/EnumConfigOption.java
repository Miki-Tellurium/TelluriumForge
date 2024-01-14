package com.mikitellurium.telluriumforge.option;

import com.mikitellurium.telluriumforge.config.TelluriumConfig;
import com.mojang.serialization.Codec;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Locale;

public class EnumConfigOption<E extends Enum<E>> extends ConfigOption<E> {

    private final Class<E> clazz;

    public EnumConfigOption(Identifier identifier, TelluriumConfig.EnumConfigEntry<E> config) {
        super(identifier, config);
        this.clazz = config.getEnumClass();
    }

    @Override
    public TelluriumConfig.EnumConfigEntry<E> getConfig() {
        return (TelluriumConfig.EnumConfigEntry<E>) super.getConfig();
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
