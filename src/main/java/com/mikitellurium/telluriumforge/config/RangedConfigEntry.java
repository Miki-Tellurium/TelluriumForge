package com.mikitellurium.telluriumforge.config;

/**
 * An object used to save a config value that has to be
 * in a certain range. To make a new entry see the
 * implementation of {@link TelluriumConfig.EntryBuilder}.
 *
 * @param <N> Is the type of {@link Number} held by this entry
 */
public class RangedConfigEntry<N extends Number> extends ConfigEntry<N> {

    private final N minValue;
    private final N maxValue;

    protected RangedConfigEntry(TelluriumConfig parent, String key, N defaultValue, N minValue, N maxValue) {
        super(parent, key, defaultValue);
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
     * Change the currently loaded value of this entry.
     * <p>
     * If the new value is out of the specified range for this entry
     * it's automatically set to the closest value inside the range.
     * <p>
     * If this is called during the execution of the game, use
     * {@link TelluriumConfig#save()} before the game close to save the
     * new value to the config file.
     * @param value the new value
     */
    @Override
    public void setValue(N value) {
        if (compare(value, minValue) < 0) {
            super.setValue(minValue);
        } else if (compare(value, maxValue) > 0){
            super.setValue(maxValue);
        } else {
            super.setValue(value);
        }
    }

    /**
     * Compares two values of type {@code T}.
     *
     * @param value1 the first value to compare
     * @param value2 the second value to compare
     * @return a negative integer if value1 is less than value2,
     *         zero if they are equal, or a positive integer if
     *         value1 is greater than value2
     */
    @SuppressWarnings("unchecked")
    private int compare(N value1, N value2) {
        return ((Comparable<N>) value1).compareTo(value2);
    }

    /**
     * Add a comment for this entry.
     * Overrides the {@link ConfigEntry#comment} method to provide a more specific
     * return type.
     * @param comment the comment to write before the entry
     * @return the config entry that was commented
     */
    @Override
    public RangedConfigEntry<N> comment(String comment) {
        super.comment(comment);
        return this;
    }

}
