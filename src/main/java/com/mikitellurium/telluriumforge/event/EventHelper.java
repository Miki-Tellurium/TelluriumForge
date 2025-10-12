package com.mikitellurium.telluriumforge.event;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventBus;

import java.util.ArrayList;
import java.util.List;

public class EventHelper {
    private final List<EventRegistration> events = new ArrayList<>();

    public EventHelper registerClass(Class<?> clazz) {
        return this.registerClass(MinecraftForge.EVENT_BUS, clazz);
    }

    public EventHelper registerClass(EventBus eventBus, Class<?> clazz) {
        events.add(new EventRegistration(eventBus, clazz));
        return this;
    }

    /**
     * Register every object. Call this after adding every listener/class.
     */
    public void registerAll() {
        this.events.forEach(EventRegistration::register);
        this.events.clear();
    }

    private static class EventRegistration {
        private final EventBus eventBus;
        private final Class<?> eventClass;

        private EventRegistration(EventBus eventBus, Class<?> eventClass) {
            this.eventBus = eventBus;
            this.eventClass = eventClass;
        }

        void register() {
            eventBus.register(eventClass);
        }
    }
}
