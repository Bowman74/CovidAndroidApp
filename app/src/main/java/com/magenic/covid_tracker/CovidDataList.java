package com.magenic.covid_tracker;

import com.magenic.covid_tracker.CovidData;

import java.util.ArrayList;

public class CovidDataList extends ArrayList<CovidData> {

    public ArrayList<String> getIsoCodes() {

        ArrayList<String> returnValue = new ArrayList<String>();

        for (CovidData curItem : this) {
            String returnItem = concatIso(curItem.getIsoCode(), curItem.getRegion());
            if (!contains(returnValue, returnItem)) {
                returnValue.add(returnItem);
            }
        }
        return  returnValue;
    }

    private Boolean contains(ArrayList<String> items, String item) {
        for (String element : items) {
            if (element.equals(item)) {
                return true;
            }
        }
        return false;
    }

    private String concatIso(String isoCode, String regionName) {
        return isoCode.concat("::").concat(regionName);
    }
}
