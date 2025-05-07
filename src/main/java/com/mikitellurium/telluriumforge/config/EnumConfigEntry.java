package com.mikitellurium.telluriumforge.config;

import com.mikitellurium.telluriumforge.config.serializer.EntryWriter;

public class EnumConfigEntry<E extends Enum<E>> extends ConfigEntry<E> {
    protected EnumConfigEntry(TelluriumConfig parent, Class<E> type, String key, E defaultValue) {
        super(parent, type, key, defaultValue);
    }

    @Override
    public String serialize(E value) {
        return value.name();
    }

    @Override
    public E deserialize(String string) {
        return E.valueOf(this.getType(), string);
    }

    @Override
    public void writeEntry(EntryWriter writer) {
        writeComments(writer, this.getComments());
        writer.write("# Options: ");
        E[] constants = this.getType().getEnumConstants();
        for (E constant : constants) {
            writer.write(this.serialize(constant));
            if (!constants[constants.length - 1].equals(constant)) {
                writer.write(", ");
            } else {
                writer.write(System.lineSeparator());
            }
        }
        writer.writeComment("Default = " + this.serialize(this.getDefault()));
        writer.writeLine(this.getKey() + "=" + this.serialize(this.get()));
    }
}