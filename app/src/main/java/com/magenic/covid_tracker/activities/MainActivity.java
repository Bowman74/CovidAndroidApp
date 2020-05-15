package com.magenic.covid_tracker.activities;

import androidx.activity.ComponentActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

import javax.inject.Inject;
import dagger.android.AndroidInjection;

import com.magenic.covid_tracker.CovidData;
import com.magenic.covid_tracker.R;
import com.magenic.covid_tracker.constants.Settings;
import com.magenic.covid_tracker.databinding.ActivityMainBindingImpl;
import com.magenic.covid_tracker.viewmodels.MainViewModel;
import com.magenic.covid_tracker.enums.DataPoint;
import com.magenic.covid_tracker.infrastructure.DIViewModelFactory;
import com.magenic.covid_tracker.views.CovidChartView;

public class MainActivity extends ComponentActivity implements Toolbar.OnMenuItemClickListener {

    @Inject
    DIViewModelFactory _dIViewModelFactory;

    MainViewModel _viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        ActivityMainBindingImpl binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        MaterialToolbar topAppBar = findViewById(R.id.mainToolbar);

        topAppBar.setOnMenuItemClickListener(this);
        _viewModel = new ViewModelProvider(this, _dIViewModelFactory).get(MainViewModel.class);
        //Todo: need some way to have a busy state while data is loading
        //Todo: should the chart view be bindable?
        _viewModel.getCovidData().observe(this, covidDataList -> {
            drawChart(covidDataList, _viewModel.get_item1Iso().getValue(), _viewModel.get_item2Iso().getValue(), _viewModel.get_metric().getValue());
        });
        _viewModel.get_metric().observe(this, newMetric -> {

            drawChart(_viewModel.getCovidData().getValue(), _viewModel.get_item1Iso().getValue(), _viewModel.get_item2Iso().getValue(), newMetric);
        });
        _viewModel.get_item1Iso().observe(this, newItem1Iso -> {

            drawChart(_viewModel.getCovidData().getValue(), newItem1Iso, _viewModel.get_item2Iso().getValue(), _viewModel.get_metric().getValue());
        });
        _viewModel.get_item2Iso().observe(this, newItem2Iso -> {

            drawChart(_viewModel.getCovidData().getValue(), _viewModel.get_item1Iso().getValue(), newItem2Iso, _viewModel.get_metric().getValue());
        });

        binding.setViewmodel(_viewModel);

        binding.setLifecycleOwner(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Settings.REQUEST_CODE) {
            _viewModel.get_item1Iso().postValue(data.getStringExtra(Settings.FIRST_COUNTRY));
            _viewModel.get_item2Iso().postValue(data.getStringExtra(Settings.SECOND_COUNTRY));
            _viewModel.get_metric().postValue(DataPoint.values()[data.getIntExtra(Settings.METRIC, 0)]);
        }
    }

    public void drawChart(List<CovidData> covidData, String item1Iso, String item2Iso, DataPoint metric) {
        CovidChartView chart = findViewById(R.id.line_chart);

        chart.setChartData(covidData, item1Iso, item2Iso, metric);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        //Todo: Settings shouldn't be able to be navigated to until data is loaded
        switch (menuItem.getItemId()) {
            case R.id.action_settings:
                Intent intent= new Intent(MainActivity.this, SettingsActivity.class);
                intent.putExtra(Settings.FIRST_COUNTRY, _viewModel.get_item1Iso().getValue());
                intent.putExtra(Settings.SECOND_COUNTRY, _viewModel.get_item2Iso().getValue());
                intent.putExtra(Settings.METRIC, _viewModel.get_metric().getValue().ordinal());
                intent.putExtra(Settings.ISO_CODES, _viewModel.getCovidData().getValue().getIsoCodes());
                startActivityForResult(intent, Settings.REQUEST_CODE);
                break;
        }
        return false;
    }
}

