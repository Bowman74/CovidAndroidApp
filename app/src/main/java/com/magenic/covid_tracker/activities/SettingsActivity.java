package com.magenic.covid_tracker.activities;

import android.content.Intent;

import androidx.activity.ComponentActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import dagger.android.AndroidInjection;

import com.magenic.covid_tracker.IsoInfo;
import com.magenic.covid_tracker.R;
import com.magenic.covid_tracker.constants.Settings;
import com.magenic.covid_tracker.databinding.ActivitySettingsBindingImpl;
import com.magenic.covid_tracker.enums.DataPoint;
import com.magenic.covid_tracker.helpers.ListHelpers;
import com.magenic.covid_tracker.infrastructure.DIViewModelFactory;
import com.magenic.covid_tracker.viewmodels.SettingsViewModel;

import javax.inject.Inject;

public class SettingsActivity extends ComponentActivity {

    @Inject
    DIViewModelFactory _dIViewModelFactory;

    SettingsViewModel _viewModel;

    @Override
    protected void onStart() {
        AndroidInjection.inject(this);

        super.onStart();
        ActivitySettingsBindingImpl binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);

        _viewModel = new ViewModelProvider(this, _dIViewModelFactory).get(SettingsViewModel.class);
        _viewModel.get_item1Iso().postValue(getIntent().getStringExtra(Settings.FIRST_COUNTRY));
        _viewModel.get_item2Iso().postValue(getIntent().getStringExtra(Settings.SECOND_COUNTRY));
        _viewModel.get_metric().postValue(DataPoint.values()[getIntent().getIntExtra(Settings.METRIC, DataPoint.TotalCases.ordinal())]);

        ArrayList<String> codes = getIntent().getStringArrayListExtra(Settings.ISO_CODES);

        for (String item : codes) {
            String[] values = item.split("::");
           _viewModel.get_isoCodes().add(new IsoInfo(values[0], values[1]));
        }

        binding.setViewmodel(_viewModel);
        binding.setListHelpers(new ListHelpers(this));

        binding.setLifecycleOwner(this);
    }

    @Override
    public void onBackPressed() {

        Intent intent=new Intent();
        intent.putExtra(Settings.FIRST_COUNTRY, _viewModel.get_item1Iso().getValue());
        intent.putExtra(Settings.SECOND_COUNTRY, _viewModel.get_item2Iso().getValue());
        intent.putExtra(Settings.METRIC, _viewModel.get_metric().getValue().ordinal());
        setResult(Settings.REQUEST_CODE,intent);

        super.onBackPressed();
    }
}