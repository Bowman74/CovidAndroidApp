package com.magenic.covid_tracker.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import com.magenic.covid_tracker.CovidDataList;
import com.magenic.covid_tracker.enums.DataPoint;
import com.magenic.covid_tracker.interfaces.ICovidDataService;

public class MainViewModel extends BaseViewModel {
    private ICovidDataService _covidDataService;

    private MutableLiveData<CovidDataList> _covidData;

    private MutableLiveData<Boolean> _dataLoaded = new MutableLiveData<Boolean>(false);

    MutableLiveData<String> _item1Iso = new MutableLiveData<String>("USA");
    MutableLiveData<String> _item2Iso = new MutableLiveData<String>("ITA");
    MutableLiveData<DataPoint> _metric = new MutableLiveData<DataPoint>(DataPoint.NewDeaths);

    @Inject
    public MainViewModel(ICovidDataService _covidDataService) {
        this._covidDataService = _covidDataService;
    }

    public LiveData<CovidDataList> getCovidData() {
        if (_covidData == null) {
            _covidData = new MutableLiveData<CovidDataList>(new CovidDataList());
            loadCovidData(_covidData);
        }

        return _covidData;
    }

    private void loadCovidData(MutableLiveData<CovidDataList> covidData) {
        ListenableFuture<CovidDataList> covidDataFuture = _covidDataService.GetCovidInformation();
        set_isBusy(true);
        set_dataLoaded(false);


        ExecutorService e = Executors.newCachedThreadPool();

        Futures.addCallback(covidDataFuture, new FutureCallback<CovidDataList>() {
            @Override
            public void onSuccess(CovidDataList returnedList) {

                covidData.postValue(returnedList);
                set_isBusy(false);
                set_dataLoaded(true);
            }

            @Override
            public void onFailure(Throwable t) {
                sendMessage("Network Error", "There was an issue retrieving the Covid data over the network. Please refresh to try again.");
                set_isBusy(false);
            }
        }, e);
    }

    public void refreshCovidData() {
        _covidData.postValue(new CovidDataList());
        loadCovidData(_covidData);
    }

    public LiveData<String> get_item1Iso() {
        return _item1Iso;
    }

    public void set_item1Iso(String item1Iso) {
        this._item1Iso.postValue(item1Iso);
    }

    public LiveData<String> get_item2Iso() {
        return _item2Iso;
    }

    public void set_item2Iso(String item2Iso) {
        this._item2Iso.postValue(item2Iso);
    }

    public LiveData<DataPoint> get_metric() {
        return _metric;
    }

    public void set_metric(DataPoint metric) {
        this._metric.postValue(metric);
    }

    public LiveData<Boolean> get_dataLoaded() {
        return _dataLoaded;
    }

    public void set_dataLoaded(Boolean dataLoaded) {
        this._dataLoaded.postValue(dataLoaded);
    }
}
