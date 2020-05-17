package com.magenic.covid_tracker;

import android.app.Activity;
import android.app.Application;

import androidx.fragment.app.Fragment;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;

import com.magenic.covid_tracker.infrastructure.DaggerICovidAppComponent;

public class CovidApplication extends Application  implements HasActivityInjector, HasSupportFragmentInjector {
    @Inject
    DispatchingAndroidInjector<Activity> _dispatchingAndroidInjector;

    @Inject
    DispatchingAndroidInjector<Fragment> _fragmentInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerICovidAppComponent.create().inject(this);
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return _dispatchingAndroidInjector;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return _fragmentInjector;
    }
}
