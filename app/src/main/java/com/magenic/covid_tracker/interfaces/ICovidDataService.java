package com.magenic.covid_tracker.interfaces;

import com.magenic.covid_tracker.CovidDataList;

import com.google.common.util.concurrent.ListenableFuture;

public interface ICovidDataService {
    ListenableFuture<CovidDataList> GetCovidInformation();
}
