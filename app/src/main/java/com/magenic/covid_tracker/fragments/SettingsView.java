package com.magenic.covid_tracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.magenic.covid_tracker.IsoInfo;
import com.magenic.covid_tracker.R;
import com.magenic.covid_tracker.databinding.SettingsviewBindingImpl;
import com.magenic.covid_tracker.helpers.ListHelpers;
import com.magenic.covid_tracker.viewmodels.MainViewModel;
import com.magenic.covid_tracker.viewmodels.SettingsViewModel;

import java.util.ArrayList;

public class SettingsView extends BaseView<SettingsViewModel> {

    MainViewModel _soureViewModel;

    public SettingsView() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SettingsviewBindingImpl binding = DataBindingUtil.inflate(inflater, R.layout.settingsview, container, false);

        NavController navController = NavHostFragment.findNavController(this);

        ViewModelStoreOwner owner = navController.getViewModelStoreOwner(R.id.settings_graph);

        createViewModel(owner, SettingsViewModel.class);

        _soureViewModel = new ViewModelProvider(getActivity(), _dIViewModelFactory).get(MainViewModel.class);


        _viewModel.set_item1Iso(_soureViewModel.get_item1Iso().getValue());
        _viewModel.get_item1Iso().observe(this.getViewLifecycleOwner(), item1Iso -> {
            _soureViewModel.set_item1Iso(item1Iso);
        });
        _viewModel.set_item2Iso(_soureViewModel.get_item2Iso().getValue());
        _viewModel.get_item2Iso().observe(this.getViewLifecycleOwner(), item2Iso -> {
            _soureViewModel.set_item2Iso(item2Iso);
        });
        _viewModel.set_metric(_soureViewModel.get_metric().getValue());
        _viewModel.get_metric().observe(this.getViewLifecycleOwner(), metric -> {
            _soureViewModel.set_metric(metric);
        });

        ArrayList<String> codes = _soureViewModel.getCovidData().getValue().getIsoCodes();

        for (String item : codes) {
            String[] values = item.split("::");
            _viewModel.get_isoCodes().add(new IsoInfo(values[1], values[0]));
        }

        binding.setViewmodel(_viewModel);
        binding.setListHelpers(new ListHelpers(this.getContext()));

        binding.setLifecycleOwner(this);

        return binding.getRoot();
    }
}
