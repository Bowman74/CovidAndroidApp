package com.magenic.covid_tracker.infrastructure;

import dagger.Binds;
import dagger.Module;

import com.magenic.covid_tracker.interfaces.ICovidDataService;
import com.magenic.covid_tracker.services.CovidDataService;

@Module
public abstract class CovidServiceModule {
    @Binds
    abstract ICovidDataService provideICovidDataService(CovidDataService covidDataService);
}


