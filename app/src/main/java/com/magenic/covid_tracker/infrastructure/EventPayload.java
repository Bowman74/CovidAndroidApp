package com.magenic.covid_tracker.infrastructure;

import java.util.HashMap;

public class EventPayload {
    private String _eventType = "";

    private HashMap<String, String> _eventItems = new HashMap<String, String>();

    public EventPayload(String eventType, HashMap<String, String> eventItems) {
        _eventType = eventType;
        _eventItems = eventItems;
    }

    public String get_eventType() {
        return _eventType;
    }

    public HashMap<String, String> get_eventItems() {
        return _eventItems;
    }

}
