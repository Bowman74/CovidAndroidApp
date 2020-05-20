package com.magenic.covid_tracker.infrastructure;

public class Event {
    private Boolean _handeled = false;

    private EventPayload _payload;

    public Event(EventPayload payload) {
        _payload = payload;
    }

    public EventPayload get_payload() {
        if (_handeled)
        {
            return null;
        }
        _handeled = true;
        return _payload;
    }

    public Boolean get_handeled() {
        return _handeled;
    }

    public EventPayload peekPayload() {
        return _payload;
    }
}
