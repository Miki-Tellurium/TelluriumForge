package com.mikitellurium.telluriumforge.config;

public class NumberConfigEntry<N extends Number> extends ConfigEntry<N> {
    protected NumberConfigEntry(TelluriumConfig parent, Class<N> type, String key, N defaultValue) {
        super(parent, type, key, defaultValue);
    }

    @Override
    public String serialize(N value) {
        return String.valueOf(value);
    }

    @Override
    public N deserialize(String string) {
        Class<N> type = this.getType();
        if (type == Integer.class) {
            return type.cast(Integer.parseInt(string));
        } else if (type == Double.class) {
            return type.cast(Double.parseDouble(string));
        } else if (type == Long.class) {
            return type.cast(Long.parseLong(string));
        } else {
            throw new IllegalStateException("Unsupported number type: " + type);
        }
    }
}
