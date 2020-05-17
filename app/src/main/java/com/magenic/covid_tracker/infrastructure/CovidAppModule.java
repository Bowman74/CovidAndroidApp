package com.magenic.covid_tracker.infrastructure;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import com.magenic.covid_tracker.activities.MainActivity;
import com.magenic.covid_tracker.fragments.MainChartView;
import com.magenic.covid_tracker.fragments.SettingsView;

@Module
public abstract class CovidAppModule {
    @ContributesAndroidInjector
    abstract MainActivity contributeActivityInjector();
    @ContributesAndroidInjector()
    abstract MainChartView mainChartView();
    @ContributesAndroidInjector()
    abstract SettingsView settingsView();
}