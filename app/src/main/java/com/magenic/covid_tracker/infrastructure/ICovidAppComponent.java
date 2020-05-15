package com.magenic.covid_tracker.infrastructure;

import com.magenic.covid_tracker.infrastructure.CovidAppModule;
import com.magenic.covid_tracker.infrastructure.CovidServiceModule;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import com.magenic.covid_tracker.CovidApplication;
import com.magenic.covid_tracker.infrastructure.ViewModelModule;

@Component(modules = { AndroidInjectionModule.class, CovidAppModule.class, CovidServiceModule.class, ViewModelModule.class})
public interface ICovidAppComponent extends AndroidInjector<CovidApplication> {
}
