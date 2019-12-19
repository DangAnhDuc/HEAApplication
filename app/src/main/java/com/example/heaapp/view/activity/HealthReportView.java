package com.example.heaapp.view.activity;

import com.example.heaapp.base.BaseView;
import com.example.heaapp.model.user_information.DailySummary;
import com.github.mikephil.charting.charts.PieChart;

public interface HealthReportView extends BaseView {

    void createPieChart(PieChart pieChart, float[] yData,String[] xData);

    void getDailySummary(DailySummary dailySummary);

    void invisiblePiechart();
}
