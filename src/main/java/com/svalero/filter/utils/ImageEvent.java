package com.svalero.filter.utils;

import javafx.event.Event;
import javafx.event.EventType;

public class ImageEvent extends Event {
    public static final EventType<ImageEvent> FILTER_APPLIED = new EventType<>("FILTER_APPLIED");

    public ImageEvent(EventType<ImageEvent> eventType) {
        super(eventType);
    }
}