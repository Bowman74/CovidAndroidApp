package com.magenic.covid_tracker.adapters;

import androidx.databinding.BindingAdapter;

import com.google.android.material.textview.MaterialTextView;
import com.magenic.covid_tracker.CovidData;
import com.magenic.covid_tracker.enums.DataPoint;

public class DataPointAdapter {
    @BindingAdapter("android:text")
    public static void setDataPoint(MaterialTextView view, DataPoint dataPoint) {
        view.setText(CovidData.getDescription(dataPoint));
    }
}