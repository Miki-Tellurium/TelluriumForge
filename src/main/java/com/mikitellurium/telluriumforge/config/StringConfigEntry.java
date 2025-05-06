package com.mikitellurium.telluriumforge.config;

public class StringConfigEntry extends ConfigEntry<String> {
    protected StringConfigEntry(TelluriumConfig parent, String key, String defaultValue) {
        super(parent, String.class, key, defaultValue);
    }

    @Override
    public String writeValue(String value) {
        return value;
    }

    @Override
    public String readValue(String string) {
        return string;
    }
}
