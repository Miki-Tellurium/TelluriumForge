package com.mikitellurium.telluriumforge.event;

import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EventHelper {

    private final List<EventRegistration> events = new ArrayList<>();

    public EventHelper() {}

    /**
     * Add a listener to the provided event bus with the default priority
     */
    public <T extends Event> EventHelper addListener(IEventBus eventBus, Consumer<T> listener) {
        return addListener(eventBus, EventPriority.NORMAL, listener);
    }

    /**
     * Add a listener to the provided event bus
     */
    public <T extends Event> EventHelper addListener(IEventBus eventBus, EventPriority priority, Consumer<T> listener) {
        events.add(new ListenerRegistration(eventBus, priority, listener));
        return this;
    }

    /**
     * Register a class to the provided event bus.
     */
    public EventHelper registerClass(IEventBus eventBus, Class<?> clazz) {
        events.add(new ClassRegistration(eventBus, clazz));
        return this;
    }

    /**
     * Register every object. Call this after adding every listener/class
     */
    public void registerAll() {
        this.events.forEach(EventRegistration::register);
        this.events.clear();
    }

    private abstract static class EventRegistration {

        private final IEventBus eventBus;

        private EventRegistration(IEventBus eventBus) {
            this.eventBus = eventBus;
        }

        public IEventBus getEventBus() {
            return eventBus;
        }

        abstract void register();

    }

    private static class ListenerRegistration extends EventRegistration {

        private final Consumer<? extends Event> listener;
        private final EventPriority priority;

        private ListenerRegistration(IEventBus eventBus, EventPriority priority, Consumer<? extends Event> listener) {
            super(eventBus);
            this.listener = listener;
            this.priority = priority;
        }

        @Override
        void register() {
            this.getEventBus().addListener(priority, listener);
        }

    }

    private static class ClassRegistration extends EventRegistration {

        private final Class<?> eventClass;

        private ClassRegistration(IEventBus eventBus, Class<?> eventClass) {
            super(eventBus);
            this.eventClass = eventClass;
        }

        @Override
        void register() {
            this.getEventBus().register(eventClass);
        }

    }

}

