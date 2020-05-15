package com.magenic.covid_tracker;

import java.util.ArrayList;
import java.util.Date;

import com.magenic.covid_tracker.enums.DataPoint;

public class CovidData {
    private String isoCode;
    private String region;
    private Date date;
    private int cases;
    private int newCases;
    private int deaths;
    private int newDeaths;

    public CovidData(String isoCode, String region, Date date, int cases, int newCases, int deaths, int newDeaths) {
        this.isoCode = isoCode;
        this.region = region;
        this.date = date;
        this.cases = cases;
        this.newCases = newCases;
        this.deaths = deaths;
        this.newDeaths = newDeaths;
    }

    public String getIsoCode() {
        return this.isoCode;
    }

    public void setIsoCode(String newRegion) {
        this.isoCode = newRegion;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCases() {
        return cases;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }

    public int getNewCases() {
        return newCases;
    }

    public void setNewCases(int newCases) {
        this.newCases = newCases;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getNewDeaths() {
        return newDeaths;
    }

    public void setNewDeaths(int newDeaths) {
        this.newDeaths = newDeaths;
    }

    public static String getDescription(DataPoint dataPoint) {
        String returnValue = "";

        switch (dataPoint) {
            case IsoCode: {
                returnValue = "IsoCode";
                break;
            }
            case Region: {
                returnValue = "Region";
                break;
            }
            case Date: {
                returnValue = "Date";
                break;
            }
            case TotalCases: {
                returnValue = "Total Cases";
                break;
            }
            case NewCases: {
                returnValue = "New Cases";
                break;
            }
            case TotalDeaths: {
                returnValue = "Total Deaths";
                break;
            }
            case NewDeaths: {
                returnValue = "New Deaths";
                break;
            }
        }
        return returnValue;
    }

    public static DataPoint getDataPointMetric(String description) {
        DataPoint returnValue = DataPoint.IsoCode;
        switch (description) {
            case "IsoCode": {
                returnValue = DataPoint.IsoCode;
                break;
            }
            case "Region": {
                returnValue = DataPoint.Region;
                break;
            }
            case "Date": {
                returnValue = DataPoint.Date;
                break;
            }
            case "Total Cases": {
                returnValue = DataPoint.TotalCases;
                break;
            }
            case "New Cases": {
                returnValue = DataPoint.NewCases;
                break;
            }
            case "Total Deaths": {
                returnValue = DataPoint.TotalDeaths;
                break;
            }
            case "New Deaths": {
                returnValue = DataPoint.NewDeaths;
                break;
            }
        }
        return returnValue;
    }

    public static String[] getMetrics() {
        ArrayList<String> returnValue = new ArrayList<String>();
        returnValue.add(getDescription(DataPoint.TotalCases));
        returnValue.add(getDescription(DataPoint.NewCases));
        returnValue.add(getDescription(DataPoint.TotalDeaths));
        returnValue.add(getDescription(DataPoint.NewDeaths));
        return returnValue.toArray(new String[0]);
    }
}
