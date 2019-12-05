package com.example.heaapp.view.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.heaapp.R;
import com.example.heaapp.presenter.HealthReportPresenterImpl;
import com.example.heaapp.service.RealmService;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HealthReportActivity extends AppCompatActivity implements HealthReportView {


    @BindView(R.id.energy_pie_chart)
    PieChart energyPieChart;
    @BindView(R.id.nutrition_pie_chart)
    PieChart nutritionPieChart;
    HealthReportPresenterImpl healthReportPresenter;
    @BindView(R.id.combinedChart)
    LineChart combinedChart;
    @BindView(R.id.healthReportToolbar)
    Toolbar healthReportToolbar;
    private int numberOfDaysInCurrentMonth;
    private ArrayList<Long> eatenEnergyInMonth;
    private ArrayList<Long> burnedEnergyInMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_report);
        ButterKnife.bind(this);

        setSupportActionBar(healthReportToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        healthReportToolbar.setNavigationOnClickListener(v -> finish());

        Calendar calendar = Calendar.getInstance();
        numberOfDaysInCurrentMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        RealmService realmService = RealmService.getInstance();
        healthReportPresenter = new HealthReportPresenterImpl(this, energyPieChart, nutritionPieChart, realmService);

        setupPieChart(energyPieChart, "Energy");
        setupPieChart(nutritionPieChart, "Nutrition");
        eatenEnergyInMonth = healthReportPresenter.getMonthlyEatenEnergy(numberOfDaysInCurrentMonth);
        burnedEnergyInMonth = healthReportPresenter.getMonthlyBurnedEnergy(numberOfDaysInCurrentMonth);
        setupCombinedChart();
        healthReportPresenter.getDailySummaryData();
    }

    private void setupCombinedChart() {
        combinedChart.getDescription().setEnabled(false);
        combinedChart.setBackgroundColor(Color.WHITE);
        combinedChart.setDrawGridBackground(true);

        YAxis rightAxis = combinedChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f);

        YAxis leftAxis = combinedChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);


        final List<String> xLabel = new ArrayList<>();
        for (int i = 1; i <= numberOfDaysInCurrentMonth; i++) {
            xLabel.add(String.valueOf(i));
        }

        XAxis xAxis = combinedChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter((value, axis) -> xLabel.get((int) value));

        LineData lineData = new LineData();
        lineData.addDataSet((ILineDataSet) generateEnergyLineData(eatenEnergyInMonth, "Daily Eaten Energy", getResources().getColor(R.color.pie_char_color_1)));
        lineData.addDataSet((ILineDataSet) generateEnergyLineData(burnedEnergyInMonth, "Daily Burned Energy", getResources().getColor(R.color.pie_char_color_2)));

        xAxis.setAxisMaximum(lineData.getXMax() + 0.25f);
        combinedChart.setData(lineData);
        combinedChart.invalidate();
    }

    private Object generateEnergyLineData(ArrayList<Long> energyInMonth, String energyLineLabel, int color) {
        LineData d = new LineData();

        ArrayList<Entry> energyEntries = new ArrayList<>();

        for (int index = 0; index < energyInMonth.size(); index++) {
            energyEntries.add(new Entry(index, energyInMonth.get(index)));
        }


        LineDataSet set = new LineDataSet(energyEntries, energyLineLabel);
        set.setColor(color);
        set.setLineWidth(2f);

        set.setCircleColor(color);
        set.setCircleHoleRadius(3f);
        set.setCircleRadius(5f);

        set.setFillColor(color);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(false);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);
        return set;
    }

    private void setupPieChart(PieChart pieChart, String chartName) {
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(50f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText(chartName);
        pieChart.setCenterTextSize(20);
        pieChart.setDrawEntryLabels(false);
        pieChart.setExtraOffsets(25.f, 0.f, 25.f, 0.f);
        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(100);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.getDescription().setEnabled(false);
    }

    @Override
    public void createPieChart(PieChart pieChart, float[] yData, String[] xData) {
        ArrayList<PieEntry> yEntrys = new ArrayList<>();

        for (int i = 0; i < yData.length; i++) {
            yEntrys.add(new PieEntry(yData[i], xData[i]));
        }

        PieDataSet pieDataSet = new PieDataSet(yEntrys, "");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(13);
        pieDataSet.setValueTextColor(Color.WHITE);


        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(getResources().getColor(R.color.pie_char_color_1));
        colors.add(getResources().getColor(R.color.pie_char_color_2));
        colors.add(getResources().getColor(R.color.pie_char_color_3));


        pieDataSet.setColors(colors);

        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);


        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.animateXY(1000, 1000);
        pieChart.invalidate();
    }

    @Override
    public Context getContext() {
        return this;
    }

}
