package com.mikitellurium.telluriumforge.event;

import net.fabricmc.fabric.api.event.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code EventHelper} class provides a simple utility for registering
 * listeners associated with various events.
 */
public class EventHelper {

    /**
     * The list of event registrations managed by this {@code EventHelper} instance.
     */
    private final List<EventRegistration<?>> events = new ArrayList<>();

    /**
     * Private constructor to enforce singleton pattern. Use {@link #getInstance()} to obtain an instance.
     */
    private EventHelper() {}

    /**
     * Gets an instance of the {@code EventHelper}.
     *
     * @return An instance of the {@code EventHelper}
     */
    public static EventHelper getInstance() {
        return new EventHelper();
    }

    /**
     * Adds a listener for a specific event.
     *
     * @param <T>      The type of event
     * @param event    The event to which the listener is added
     * @param listener The listener to be added
     * @return The current {@code EventHelper} instance for method chaining
     */
    public <T> EventHelper addListener(Event<T> event, T listener) {
        events.add(new EventRegistration<>(event, listener));
        return this;
    }

    /**
     * Registers all the added event listeners and clears the list of registered events.
     */
    public void registerAll() {
        this.events.forEach(EventRegistration::register);
        this.events.clear();
    }

    /**
     * A record representing the registration of a listener for a specific event.
     *
     * @param <T> The type of event
     */
    private record EventRegistration<T> (Event<T> event, T listener) {

        /**
         * Registers the associated listener to the event.
         */
        private void register() {
            event.register(listener);
        }

    }

}

