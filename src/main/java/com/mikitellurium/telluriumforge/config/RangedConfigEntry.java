package com.mikitellurium.telluriumforge.config;

import com.mikitellurium.telluriumforge.config.writer.EntryWriter;

/**
 * An object used to save a config value that has to be
 * in a certain range. To make a new entry see the
 * implementation of {@link TelluriumConfig.EntryBuilder}.
 *
 * @param <N> Is the type of {@link Number} held by this entry
 */
public class RangedConfigEntry<N extends Number & Comparable<N>> extends NumberConfigEntry<N> {
    private final N minValue;
    private final N maxValue;

    protected RangedConfigEntry(TelluriumConfig parent, Class<N> type, String key, N defaultValue, N minValue, N maxValue) {
        super(parent, type, key, defaultValue);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    /**
     * @return the minimum value this entry can have
     */
    public N getMinValue() {
        return minValue;
    }

    /**
     * @return the maximum value this entry can have
     */
    public N getMaxValue() {
        return maxValue;
    }

    /**
     * Change the currently loaded value of this entry.<br>
     * If the new value is out of the specified range for this entry
     * it's automatically set to the closest value inside the range.<br>
     * If this is called during the execution of the game, use
     * {@link TelluriumConfig#save()} before the game close to save the
     * new value to the config file.
     * @param value the new value
     */
    @Override
    public void set(N value) {
        if (compare(value, minValue) < 0) {
            super.set(minValue);
        } else if (compare(value, maxValue) > 0){
            super.set(maxValue);
        } else {
            super.set(value);
        }
    }

    /**
     * Compares the numbers provided.
     */
    private int compare(N value1, N value2) {
        return value1.compareTo(value2);
    }

    @Override
    public void writeEntry(EntryWriter writer) {
        writeComments(writer, this.getComments());
        N minValue = this.getMinValue();
        N maxValue = this.getMaxValue();
        writer.writeComment("Range: min=" + this.serialize(minValue) + ", max=" + this.serialize(maxValue));
        writer.writeComment("Default = " + this.serialize(this.getDefault()));
        writer.writeLine(this.getKey() + "=" + this.serialize(this.get()));
    }
}
