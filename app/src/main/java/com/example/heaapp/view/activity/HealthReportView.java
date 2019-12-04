package com.example.heaapp.view.activity;

import com.example.heaapp.base.BaseView;
import com.github.mikephil.charting.charts.PieChart;

public interface HealthReportView extends BaseView {

    void createPieChart(PieChart pieChart, float[] yData,String[] xData);

}
