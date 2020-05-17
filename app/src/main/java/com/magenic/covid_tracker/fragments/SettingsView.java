package com.magenic.covid_tracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.magenic.covid_tracker.IsoInfo;
import com.magenic.covid_tracker.R;
import com.magenic.covid_tracker.databinding.SettingsviewBindingImpl;
import com.magenic.covid_tracker.helpers.ListHelpers;
import com.magenic.covid_tracker.infrastructure.DIViewModelFactory;
import com.magenic.covid_tracker.viewmodels.MainViewModel;
import com.magenic.covid_tracker.viewmodels.SettingsViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class SettingsView extends Fragment {
    @Inject
    DIViewModelFactory _dIViewModelFactory;

    SettingsViewModel _viewModel;

    MainViewModel _soureViewModel;

    public SettingsView() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SettingsviewBindingImpl binding = DataBindingUtil.inflate(inflater, R.layout.settingsview, container, false);

        _viewModel = new ViewModelProvider(this, _dIViewModelFactory).get(SettingsViewModel.class);

        _soureViewModel = new ViewModelProvider(getActivity(), _dIViewModelFactory).get(MainViewModel.class);

        _viewModel.get_item1Iso().setValue(_soureViewModel.get_item1Iso().getValue());
        _viewModel.get_item1Iso().observe(this.getViewLifecycleOwner(), item1Iso -> {
            _soureViewModel.get_item1Iso().postValue(item1Iso);
        });
        _viewModel.get_item2Iso().setValue(_soureViewModel.get_item2Iso().getValue());
        _viewModel.get_item2Iso().observe(this.getViewLifecycleOwner(), item2Iso -> {
            _soureViewModel.get_item2Iso().postValue(item2Iso);
        });
        _viewModel.get_metric().setValue(_soureViewModel.get_metric().getValue());
        _viewModel.get_metric().observe(this.getViewLifecycleOwner(), metric -> {
            _soureViewModel.get_metric().postValue(metric);
        });

        ArrayList<String> codes = _soureViewModel.getCovidData().getValue().getIsoCodes();

        for (String item : codes) {
            String[] values = item.split("::");
            _viewModel.get_isoCodes().add(new IsoInfo(values[0], values[1]));
        }

        binding.setViewmodel(_viewModel);
        binding.setListHelpers(new ListHelpers(this.getContext()));

        binding.setLifecycleOwner(this);

        return binding.getRoot();
    }

}
