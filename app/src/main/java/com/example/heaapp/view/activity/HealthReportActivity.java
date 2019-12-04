package com.example.heaapp.view.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.heaapp.R;
import com.example.heaapp.presenter.HealthReportPresenterImpl;
import com.example.heaapp.service.RealmService;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HealthReportActivity extends AppCompatActivity implements HealthReportView, OnChartValueSelectedListener {


    @BindView(R.id.energy_pie_chart)
    PieChart energyPieChart;
    @BindView(R.id.nutrition_pie_chart)
    PieChart nutritionPieChart;
    HealthReportPresenterImpl healthReportPresenter;
    @BindView(R.id.combinedChart)
    CombinedChart combinedChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_report);
        ButterKnife.bind(this);
        RealmService realmService = RealmService.getInstance();
        setupPieChart(energyPieChart,"Energy");
        setupPieChart(nutritionPieChart,"Nutrition");
        setupCombinedChart();
        healthReportPresenter = new HealthReportPresenterImpl(this, energyPieChart, nutritionPieChart, realmService);
        healthReportPresenter.getDailySummaryData();
    }

    private void setupCombinedChart() {
        combinedChart.getDescription().setEnabled(false);
        combinedChart.setBackgroundColor(Color.WHITE);
        combinedChart.setDrawGridBackground(false);
        combinedChart.setDrawBarShadow(false);
        combinedChart.setHighlightFullBarEnabled(false);
        combinedChart.setOnChartValueSelectedListener(this);

        YAxis rightAxis = combinedChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f);

        YAxis leftAxis = combinedChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);

        final List<String> xLabel = new ArrayList<>();
        xLabel.add("Jan");
        xLabel.add("Feb");
        xLabel.add("Mar");
        xLabel.add("Apr");
        xLabel.add("May");
        xLabel.add("Jun");
        xLabel.add("Jul");
        xLabel.add("Aug");
        xLabel.add("Sep");
        xLabel.add("Oct");
        xLabel.add("Nov");
        xLabel.add("Dec");

        XAxis xAxis = combinedChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xLabel.get((int) value % xLabel.size());
            }
        });

        CombinedData data = new CombinedData();
        LineData lineDatas = new LineData();
        lineDatas.addDataSet((ILineDataSet) dataChart());

        data.setData(lineDatas);

        xAxis.setAxisMaximum(data.getXMax() + 0.25f);

        combinedChart.setData(data);
        combinedChart.invalidate();
    }

    private static DataSet dataChart() {

        LineData d = new LineData();
        int[] data = new int[] { 1, 2, 2, 1, 1, 1, 2, 1, 1, 2, 1, 9 };

        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int index = 0; index < 12; index++) {
            entries.add(new Entry(index, data[index]));
        }

        LineDataSet set = new LineDataSet(entries, "Request Ots approved");
        set.setColor(Color.GREEN);
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.GREEN);
        set.setCircleRadius(5f);
        set.setFillColor(Color.GREEN);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.GREEN);

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
        ArrayList<String> xEntrys = new ArrayList<>();

        for (int i = 0; i < yData.length; i++) {
            yEntrys.add(new PieEntry(yData[i], xData[i]));
        }
        for (int i = 0; i < xData.length; i++) {
            xEntrys.add(xData[i]);
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
        pieChart.animateY(1000);
        pieChart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public Context getContext() {
        return this;
    }

}
