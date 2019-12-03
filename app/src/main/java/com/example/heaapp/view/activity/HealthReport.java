package com.example.heaapp.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.heaapp.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HealthReport extends AppCompatActivity implements OnChartValueSelectedListener {

    @BindView(R.id.piechart)
    PieChart piechart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_report);
        ButterKnife.bind(this);

        piechart.setRotationEnabled(true);
        piechart.setDescription(new Description());
        piechart.setHoleRadius(50f);
        piechart.setTransparentCircleAlpha(0);
        piechart.setCenterText("PieChart");
        piechart.setCenterTextSize(10);
        piechart.setDrawEntryLabels(true);
        piechart.setTransparentCircleAlpha(100);
        piechart.setTransparentCircleColor(Color.WHITE);

        addDataSet(piechart);
        piechart.setOnChartValueSelectedListener(this);
    }

    private void addDataSet(PieChart piechart) {
        ArrayList<PieEntry> yEntrys= new ArrayList<>();
        ArrayList<String> xEntrys=new ArrayList<>();

        float[] yData = { 25, 40, 70 };
        String[] xData = { "January", "February", "January" };

        for (int i = 0; i < yData.length;i++){
            yEntrys.add(new PieEntry(yData[i],i));
        }
        for (int i = 0; i < xData.length;i++){
            xEntrys.add(xData[i]);
        }

        PieDataSet pieDataSet=new PieDataSet(yEntrys,"Employee Sales");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);


        ArrayList<Integer> colors=new ArrayList<>();
        colors.add(getResources().getColor(R.color.colorPrimaryDark));
        colors.add(getResources().getColor(R.color.colorPrimary));
        colors.add(getResources().getColor(R.color.water_color));

        pieDataSet.setColors(colors);

        Legend legend=piechart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        PieData pieData=new PieData(pieDataSet);
        piechart.setData(pieData);
        piechart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Toast.makeText(this, "Value: "
                + e.getY()
                + ", index: "
                + h.getX()
                + ", DataSet index: "
                + h.getDataSetIndex(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected() {

    }
}
