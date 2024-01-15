package com.mikitellurium.telluriumforge.util;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A simple utility class representing a cached object that can be either non-null or empty.
 *
 * @param <T> The type of the cached object
 */
public class CachedObject<T> implements Supplier<T> {

    /**
     * The cached object.
     */
    private final T obj;

    /**
     * Constructs a {@code CachedObject} with a non-null object.
     *
     * @param obj The non-null object to be cached
     */
    private CachedObject(T obj) {
        this.obj = obj;
    }

    /**
     * Constructs an empty {@code CachedObject}
     */
    private CachedObject() {
        this.obj = null;
    }

    /**
     * Static factory method to create a {@code CachedObject} with a specified object.
     *
     * @param obj The object to be cached
     * @return A {@code CachedObject} containing the specified object
     */
    public static CachedObject<?> of(Object obj) {
        return new CachedObject<>(obj);
    }

    /**
     * Static factory method to create an empty {@code CachedObject}.
     *
     * @return An empty {@code CachedObject}
     */
    public static CachedObject<?> empty() {
        return new CachedObject<>();
    }

    /**
     * Retrieves the cached object.
     *
     * @return The cached object, which may be null for an empty {@code CachedObject}
     */
    @Override
    public T get() {
        return this.obj;
    }

    /**
     * Checks if the {@code CachedObject} is empty.
     *
     * @return true if the {@code CachedObject} is empty (contains null), otherwise false
     */
    public boolean isEmpty() {
        return this.obj == null;
    }

    /**
     * Checks if the {@code CachedObject} is non-empty.
     *
     * @return true if the {@code CachedObject} is non-empty (contains a non-null object), otherwise false
     */
    public boolean isPresent() {
        return !isEmpty();
    }

    /**
     * Performs the specified action on the cached object if it is non-empty.
     *
     * @param consumer The action to be performed on the cached object
     */
    public void ifPresent(Consumer<T> consumer) {
        if (isPresent()) {
            consumer.accept(this.get());
        }
    }

}
