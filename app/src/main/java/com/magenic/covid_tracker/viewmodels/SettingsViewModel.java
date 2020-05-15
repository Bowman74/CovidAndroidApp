package com.magenic.covid_tracker.viewmodels;

import androidx.lifecycle.MutableLiveData;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.magenic.covid_tracker.CovidData;
import com.magenic.covid_tracker.IsoInfo;
import com.magenic.covid_tracker.enums.DataPoint;
import com.magenic.covid_tracker.interfaces.IListHelpers;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class SettingsViewModel extends BaseViewModel {

    MutableLiveData<String> _item1Iso = new MutableLiveData<String>("USA");
    MutableLiveData<String> _item2Iso = new MutableLiveData<String>("ITA");
    MutableLiveData<DataPoint> _metric = new MutableLiveData<DataPoint>(DataPoint.NewDeaths);
    ArrayList<IsoInfo> _isoCodes = new ArrayList<IsoInfo>();

    @Inject
    public SettingsViewModel() { }

    public MutableLiveData<String> get_item1Iso() {
        return _item1Iso;
    }

    public MutableLiveData<String> get_item2Iso() {
        return _item2Iso;
    }

    public MutableLiveData<DataPoint> get_metric() {
        return _metric;
    }

    public ArrayList<IsoInfo> get_isoCodes() {
        return _isoCodes;
    }

    public String getIsoFromRegion(String region) {
        for (IsoInfo info : _isoCodes) {
            if (info.get_region().equals(region)) {
                return info.get_isoCode();
            }
        }
        return "";
    }

    public String[] getRegionNameArray() {
        ArrayList<String> returnValues = new ArrayList<String>();

        for (IsoInfo info : _isoCodes) {
            returnValues.add(info.get_region());
        }
        return returnValues.toArray(new String[0]);
    }

    public void getNewItem1Iso(IListHelpers listHelpers) {
        ListenableFuture<String> listFuture = listHelpers.showStringListForResult(getRegionNameArray(), "Select Region");
        ExecutorService e = Executors.newSingleThreadExecutor();

        Futures.addCallback(listFuture, new FutureCallback<String>() {
            @Override
            public void onSuccess(String returnedItem) {
                if(!returnedItem.equals("")) {
                    get_item1Iso().postValue(getIsoFromRegion(returnedItem));
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        }, e);
    }

    public void getNewItem2Iso(IListHelpers listHelpers) {
        ListenableFuture<String> listFuture = listHelpers.showStringListForResult(getRegionNameArray(), "Select Region");
        ExecutorService e = Executors.newSingleThreadExecutor();

        Futures.addCallback(listFuture, new FutureCallback<String>() {
            @Override
            public void onSuccess(String returnedItem) {
                if(!returnedItem.equals("")) {
                    get_item2Iso().postValue(getIsoFromRegion(returnedItem));
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        }, e);
    }

    public void getNewMetric(IListHelpers listHelpers) {
        ListenableFuture<String> listFuture = listHelpers.showStringListForResult(CovidData.getMetrics(), "Select Metric");
        ExecutorService e = Executors.newSingleThreadExecutor();

        Futures.addCallback(listFuture, new FutureCallback<String>() {
            @Override
            public void onSuccess(String returnedItem) {
                if(!returnedItem.equals("")) {
                    get_metric().postValue(CovidData.getDataPointMetric((returnedItem)));
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        }, e);
    }
}
