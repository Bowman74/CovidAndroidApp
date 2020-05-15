package com.magenic.covid_tracker.services;

import android.os.AsyncTask;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;

import javax.inject.Inject;

import com.magenic.covid_tracker.CovidData;
import com.magenic.covid_tracker.CovidDataList;
import com.magenic.covid_tracker.enums.DataPoint;
import com.magenic.covid_tracker.interfaces.ICovidDataService;

public class CovidDataService implements ICovidDataService {
    @Inject
    public CovidDataService() {
    }

    @Override
    public ListenableFuture<CovidDataList> GetCovidInformation() {
        final SettableFuture<CovidDataList> result = SettableFuture.create();

        Runnable networkTask = new Runnable() {

            @Override
            public void run() {
                try {
                    final CovidDataList returnInformation = new CovidDataList();
                    final String covidEndpoint = "https://covid.ourworldindata.org/data/owid-covid-data.csv";

                    URL url = new URL(covidEndpoint);

                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                    BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

                    String line = br.readLine();
                    // Ignore header
                    line = br.readLine();

                    while (line != null) {

                        String[] attributes = line.split(",");

                        if (!attributes[0].isEmpty()) {
                            //Todo: Add more robust handling of parsing of values, some error could happed if input data is bad
                            CovidData newData = new CovidData(attributes[DataPoint.IsoCode.ordinal()], attributes[DataPoint.Region.ordinal()],
                                    new SimpleDateFormat("yyyy-MM-dd").parse(attributes[DataPoint.Date.ordinal()]), Integer.parseInt(attributes[DataPoint.TotalCases.ordinal()]),
                                    Integer.parseInt(attributes[DataPoint.NewCases.ordinal()]), Integer.parseInt(attributes[DataPoint.TotalDeaths.ordinal()]),
                                    Integer.parseInt(attributes[DataPoint.NewDeaths.ordinal()]));

                            returnInformation.add(newData);
                        }

                        line = br.readLine();
                    }

                    result.set(returnInformation);

                } catch (Exception e) {
                    e.printStackTrace();
                    result.setException(e);
                }
            }
        };

        AsyncTask.execute(networkTask);

        return result;
    }

}
