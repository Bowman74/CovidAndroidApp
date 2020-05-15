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

        ExecutorService e = Executors.newCachedThreadPool();

        Futures.addCallback(covidDataFuture, new FutureCallback<CovidDataList>() {
            @Override
            public void onSuccess(CovidDataList returnedList) {
                covidData.postValue(returnedList);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        }, e);
    }

    public MutableLiveData<String> get_item1Iso() {
        return _item1Iso;
    }


    public MutableLiveData<String> get_item2Iso() {
        return _item2Iso;
    }

    public MutableLiveData<DataPoint> get_metric() {  return _metric; }
}
