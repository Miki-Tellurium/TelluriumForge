package com.mikitellurium.telluriumforge.config.serializer;

public interface EntryWriter {
    void write(String s);
    void writeLine(String s);
    void writeComment(String comment);
}
