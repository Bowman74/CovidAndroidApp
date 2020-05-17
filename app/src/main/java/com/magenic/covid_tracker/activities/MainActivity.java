package com.magenic.covid_tracker.activities;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import javax.inject.Inject;
import dagger.android.AndroidInjection;

import com.magenic.covid_tracker.R;
import com.magenic.covid_tracker.viewmodels.MainViewModel;
import com.magenic.covid_tracker.infrastructure.DIViewModelFactory;

public class MainActivity extends FragmentActivity {

    @Inject
    DIViewModelFactory _dIViewModelFactory;

    MainViewModel _viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_main);
    }
}

