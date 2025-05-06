package com.mikitellurium.telluriumforge.config.serializer;

import org.slf4j.Logger;

import java.io.FileWriter;
import java.io.IOException;

public final class EntryWriterImpl implements EntryWriter {
    private final FileWriter writer;
    private final String filePath;
    private final Logger logger;

    public EntryWriterImpl(FileWriter writer, String filePath, Logger logger) {
        this.writer = writer;
        this.filePath = filePath;
        this.logger = logger;
    }

    @Override
    public void write(String s) {
        try {
            writer.write(s);
        } catch (IOException e) {
            logger.error("Something went wrong when trying to write config file \"{}\"", filePath, e);
        }
    }

    @Override
    public void writeLine(String s) {
        this.write(s + System.lineSeparator());
    }

    @Override
    public void writeComment(String comment) {
        this.writeLine("# " + comment);
    }
}
