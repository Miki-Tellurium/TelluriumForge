package com.mikitellurium.telluriumforge.config;

public class BooleanConfigEntry extends ConfigEntry<Boolean> {
    protected BooleanConfigEntry(TelluriumConfig parent, String key, Boolean defaultValue) {
        super(parent, Boolean.class, key, defaultValue);
    }

    @Override
    public String serialize(Boolean value) {
        return String.valueOf(value);
    }

    @Override
    public Boolean deserialize(String string) {
        return Boolean.parseBoolean(string);
    }
}
