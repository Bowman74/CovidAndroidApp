package com.magenic.covid_tracker.infrastructure;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import com.magenic.covid_tracker.activities.MainActivity;
import com.magenic.covid_tracker.activities.SettingsActivity;

@Module
public abstract class CovidAppModule {
    @ContributesAndroidInjector
    abstract MainActivity contributeActivityInjector();
    @ContributesAndroidInjector()
    abstract SettingsActivity settingsActivity();
}