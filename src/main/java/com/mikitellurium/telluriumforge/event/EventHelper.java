package com.mikitellurium.telluriumforge.event;

import net.fabricmc.fabric.api.event.Event;

import java.util.ArrayList;
import java.util.List;

public class EventHelper {

    private final List<EventRegistration<?>> events = new ArrayList<>();

    public static EventHelper getInstance() {
        return new EventHelper();
    }

    private EventHelper() {}

    public <T> EventHelper addListener(Event<T> event, T listener) {
        events.add(new EventRegistration<>(event, listener));
        return this;
    }

    public void registerAll() {
        this.events.forEach((EventRegistration::register));
    }

    private record EventRegistration<T> (Event<T> event, T listener) {

        public void register() {
            event.register(listener);
        }

    }

}
