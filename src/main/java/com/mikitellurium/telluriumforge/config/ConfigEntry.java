package com.mikitellurium.telluriumforge.config;

import com.mikitellurium.telluriumforge.config.serializer.EntryWriter;

import java.util.ArrayList;
import java.util.List;

public abstract class ConfigEntry<T> implements IConfigEntry<T> {
    private final TelluriumConfig builder;
    private final List<String> comments = new ArrayList<>();
    private final Class<T> type;
    private final String key;
    private final T defaultValue;
    private T value;

    protected ConfigEntry(TelluriumConfig parent, Class<T> type, String key, T defaultValue) {
        this.builder = parent;
        this.type = type;
        this.key = key;
        this.defaultValue = defaultValue;
    }

    @Override
    public TelluriumConfig getParentConfig() {
        return builder;
    }

    @Override
    public Class<T> getType() {
        return type;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public T getDefault() {
        return defaultValue;
    }

    @Override
    public T get() {
        return value != null ? value : defaultValue;
    }

    @Override
    public void set(T value) {
        this.value = value;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E extends IConfigEntry<T>> E comment(String comment) {
        this.comments.add(comment);
        return (E) this;
    }

    @Override
    public List<String> getComments() {
        return comments;
    }

    @Override
    public void writeEntry(EntryWriter writer) {
        List<String> comments = this.getComments();
        if (!comments.isEmpty()) {
            for (String c : comments) {
                writer.writeComment(c);
            }
        }
        writer.writeComment("Default = " + this.writeValue(this.getDefault()));
        writer.writeLine(this.getKey() + "=" + this.writeValue(this.get()));
    }
}
