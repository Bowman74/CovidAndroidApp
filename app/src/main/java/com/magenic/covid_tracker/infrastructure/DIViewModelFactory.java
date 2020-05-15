package com.magenic.covid_tracker.infrastructure;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import com.magenic.covid_tracker.viewmodels.BaseViewModel;

public class DIViewModelFactory implements ViewModelProvider.Factory {
    private Map<Class<? extends BaseViewModel>, Provider<BaseViewModel>> _providers;

    @Inject
    public DIViewModelFactory(@NonNull Map<Class<? extends BaseViewModel>, Provider<BaseViewModel>> providers) {
        _providers = providers;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (_providers.containsKey(modelClass)) {
            return (T)_providers.get(modelClass).get();
        }
        return null;
    }
}
