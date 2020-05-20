package com.magenic.covid_tracker.adapters;

import android.view.View;
import android.widget.RelativeLayout;

import androidx.databinding.BindingAdapter;

public class BusyAdapter {
    @BindingAdapter("android:visibility")
    public static void setVisibility(RelativeLayout view, Boolean isBusy) {
        view.setVisibility(isBusy ? View.VISIBLE : View.GONE);
    }
}
