package com.magenic.covid_tracker.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.util.AttributeSet;

import androidx.annotation.NonNull;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.magenic.covid_tracker.CovidData;
import com.magenic.covid_tracker.enums.DataPoint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//Todo: occasionally this chart crashes the app, investigate why. Resources not being properly cleaned?
public class CovidChartView extends LineChart {

    public CovidChartView(Context context) {
        super(context);
    }

    public CovidChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CovidChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setChartData(@NonNull List<CovidData> covidData, @NonNull String item1Iso, @NonNull String item2Iso, DataPoint metric) {

        setBackgroundColor(Color.WHITE);

        getDescription().setEnabled(false);

        setTouchEnabled(true);

        setDrawGridBackground(false);

        setDragEnabled(true);
        setScaleEnabled(true);

        setPinchZoom(true);

        XAxis xAxis;
        {
            xAxis = getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextSize(10f);
            xAxis.setTextColor(Color.WHITE);
            xAxis.setDrawAxisLine(false);
            xAxis.setDrawGridLines(true);
            xAxis.setTextColor(Color.BLACK);
            xAxis.setCenterAxisLabels(true);
            xAxis.setGranularity(24f * 28f); // two fortnights
            xAxis.setValueFormatter(new ValueFormatter() {

                private final SimpleDateFormat format = new SimpleDateFormat("MM-dd", Locale.ENGLISH);

                @Override
                public String getFormattedValue(float value) {
                    return format.format(new Date((long)value));
                }
            });
        }



        YAxis yAxis;
        {
            yAxis = getAxisLeft();

            getAxisRight().setEnabled(false);

            yAxis.enableGridDashedLine(10f, 10f, 0f);

        }

        setData(covidData, item1Iso, item2Iso, metric);

        animateX(1500);

        Legend l = getLegend();

        l.setForm(Legend.LegendForm.LINE);
        l.setStackSpace(10);
    }

    private void setData(@NonNull List<CovidData> covidData, @NonNull String item1Iso, @NonNull String item2Iso, DataPoint metric) {

        ArrayList<Entry> country1 = new ArrayList<>();
        ArrayList<Entry> country2 = new ArrayList<>();
        String countryLabel1 = "";
        String countryLabel2 = "";

        for (int i = 0; i < covidData.size(); i++) {
            CovidData curItem = covidData.get(i);
            if (curItem.getIsoCode().equals(item1Iso)) {
                country1.add(new Entry(curItem.getDate().getTime(), getValue(curItem, metric)));
                if (countryLabel1.isEmpty()) {
                    countryLabel1 = curItem.getRegion();
                }
            } else if (curItem.getIsoCode().equals(item2Iso)) {
                country2.add(new Entry(curItem.getDate().getTime(), getValue(curItem, metric)));
                if (countryLabel2.isEmpty()) {
                    countryLabel2 = curItem.getRegion();
                }
            }
        }

        LineDataSet set1;
        LineDataSet set2;

        if (getData() != null &&
                getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) getData().getDataSetByIndex(0);
            set1.setValues(country1);
            set1.setLabel(countryLabel1);
            set1.notifyDataSetChanged();
            set2 = (LineDataSet) getData().getDataSetByIndex(1);
            set2.setValues(country2);
            set2.setLabel(countryLabel2);
            set2.notifyDataSetChanged();
            getData().notifyDataChanged();
            notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(country1, countryLabel1);
            set2 = new LineDataSet(country2, countryLabel2);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();

            addLineDataSet(set1, dataSets, Color.BLUE);
            addLineDataSet(set2, dataSets, Color.RED);

            LineData data = new LineData(dataSets);

            setData(data);
        }
    }

    private void addLineDataSet(@NonNull LineDataSet dataSet, @NonNull ArrayList<ILineDataSet> dataSets, int color) {
        dataSet.setDrawIcons(false);

        dataSet.enableDashedLine(10f, 5f, 0f);

        dataSet.setColor(color);
        dataSet.setCircleColor(color);

        dataSet.setLineWidth(1f);
        dataSet.setCircleRadius(3f);

        dataSet.setDrawCircleHole(false);

        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        dataSet.setFormLineWidth(1f);
        dataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        dataSet.setFormSize(15.f);

        dataSet.setValueTextSize(9f);

        dataSet.enableDashedHighlightLine(10f, 5f, 0f);

        dataSets.add(dataSet);
    }

    private int getValue(@NonNull CovidData covidData, @NonNull DataPoint propertyToGet) {
        int returnValue = 0;
        switch (propertyToGet) {
            case TotalCases:
                returnValue = covidData.getCases();
                break;
            case NewCases:
                returnValue = covidData.getNewCases();
                break;
            case TotalDeaths:
                returnValue = covidData.getDeaths();
                break;
            case NewDeaths:
                returnValue = covidData.getNewDeaths();
                break;
        }
        return returnValue;
    }
}
