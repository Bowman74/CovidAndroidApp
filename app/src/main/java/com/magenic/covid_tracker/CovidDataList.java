package com.magenic.covid_tracker;

import java.util.ArrayList;
import java.util.Collections;

public class CovidDataList extends ArrayList<CovidData> {

    public ArrayList<String> getIsoCodes() {
        ArrayList<String> returnValue = new ArrayList<String>();
        Integer i = 0;
        for (CovidData curItem : this) {
            String returnItem = concatIso(curItem.getIsoCode(), curItem.getRegion());
            if (!contains(returnValue, returnItem)) {
                returnValue.add(returnItem);
            }
        }
        Collections.sort(returnValue);
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
        return regionName.concat("::").concat(isoCode);
    }
}
