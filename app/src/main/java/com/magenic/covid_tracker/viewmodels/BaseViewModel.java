package com.magenic.covid_tracker.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.magenic.covid_tracker.constants.EventNames;
import com.magenic.covid_tracker.infrastructure.Event;
import com.magenic.covid_tracker.infrastructure.EventPayload;

import java.util.HashMap;

public abstract class BaseViewModel extends ViewModel {
    MutableLiveData<Boolean> _isBusy = new MutableLiveData<Boolean>(false);

    MutableLiveData<Event> _event = new MutableLiveData<Event>(null);

    public LiveData<Boolean> get_isBusy() {
        return _isBusy;
    }

    public LiveData<Event> get_event() {
        return _event;
    }

    public void set_isBusy(Boolean isBusy) {
        this._isBusy.postValue(isBusy);
    }

    protected void sendMessage(String title, String body) {
        HashMap<String, String> payload = new HashMap<String, String>();
        payload.put(EventNames.Title, title);
        payload.put(EventNames.Body, body);
        _event.postValue(new Event(new EventPayload(EventNames.Notification, payload)));
    }
}