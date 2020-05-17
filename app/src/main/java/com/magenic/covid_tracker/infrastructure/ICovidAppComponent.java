package com.magenic.covid_tracker.infrastructure;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import com.magenic.covid_tracker.CovidApplication;

@Component(modules = { AndroidInjectionModule.class, CovidAppModule.class, CovidServiceModule.class, ViewModelModule.class})
public interface ICovidAppComponent extends AndroidInjector<CovidApplication> {
}
