package com.magenic.covid_tracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.appbar.MaterialToolbar;
import com.magenic.covid_tracker.CovidData;
import com.magenic.covid_tracker.R;
import com.magenic.covid_tracker.databinding.MainchartviewBindingImpl;
import com.magenic.covid_tracker.enums.DataPoint;
import com.magenic.covid_tracker.infrastructure.DIViewModelFactory;
import com.magenic.covid_tracker.viewmodels.MainViewModel;
import com.magenic.covid_tracker.views.CovidChartView;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class MainChartView extends BaseView<MainViewModel> implements Toolbar.OnMenuItemClickListener {
    @Inject
    DIViewModelFactory _dIViewModelFactory;

    public MainChartView() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        MainchartviewBindingImpl binding = DataBindingUtil.inflate(inflater, R.layout.mainchartview, container, false);

        MaterialToolbar topAppBar = binding.getRoot().findViewById(R.id.mainToolbar);

        topAppBar.setOnMenuItemClickListener(this);
        createViewModel(MainViewModel.class);
        //Todo: should the chart view be bindable?
        _viewModel.getCovidData().observe(this.getViewLifecycleOwner(), covidDataList -> {
            drawChart(covidDataList, _viewModel.get_item1Iso().getValue(), _viewModel.get_item2Iso().getValue(), _viewModel.get_metric().getValue());
        });
        _viewModel.get_metric().observe(this.getViewLifecycleOwner(), newMetric -> {
            drawChart(_viewModel.getCovidData().getValue(), _viewModel.get_item1Iso().getValue(), _viewModel.get_item2Iso().getValue(), newMetric);
        });
        _viewModel.get_item1Iso().observe(this.getViewLifecycleOwner(), newItem1Iso -> {
            drawChart(_viewModel.getCovidData().getValue(), newItem1Iso, _viewModel.get_item2Iso().getValue(), _viewModel.get_metric().getValue());
        });
        _viewModel.get_item2Iso().observe(this.getViewLifecycleOwner(), newItem2Iso -> {
            drawChart(_viewModel.getCovidData().getValue(), _viewModel.get_item1Iso().getValue(), newItem2Iso, _viewModel.get_metric().getValue());
        });
        _viewModel.get_isBusy().observe(this.getViewLifecycleOwner(), isBusy -> {
            MaterialToolbar item = this.getView().findViewById(R.id.mainToolbar);
            if (item != null) {
                MenuItem menuItem = item.getMenu().getItem(1);

                if (menuItem != null) {
                    menuItem.setEnabled(!isBusy);
                }
            }
        });
        _viewModel.get_dataLoaded().observe(this.getViewLifecycleOwner(), dataLoaded -> {
            MaterialToolbar item = this.getView().findViewById(R.id.mainToolbar);
            if (item != null) {
                MenuItem menuItem = item.getMenu().getItem(0);

                if (menuItem != null) {
                    menuItem.setEnabled(dataLoaded);
                }
            }
        });

        binding.setViewmodel(_viewModel);

        binding.setLifecycleOwner(this);

        return binding.getRoot();
    }


    public void drawChart(List<CovidData> covidData, String item1Iso, String item2Iso, DataPoint metric) {
        CovidChartView chart = this.getActivity().findViewById(R.id.line_chart);

        chart.setChartData(covidData, item1Iso, item2Iso, metric);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        //Todo: Settings shouldn't be able to be navigated to until data is loaded
        switch (menuItem.getItemId()) {
            case R.id.action_settings:
                NavController navController = Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment);
                navController.navigate(R.id.navigate_to_settings_view);

                break;
            case R.id.action_refresh:
                _viewModel.refreshCovidData();
                break;
        }
        return false;
    }
}
