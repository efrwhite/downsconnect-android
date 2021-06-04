package com.example.downsconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import com.example.downsconnect.objects.Head;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);


        //Hardcoded values for practice, replace with actual national averages and child data later
        LineChart lineChart = (LineChart) findViewById(R.id.growth_chart);
        Button back = new Button(this);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final int childID = sharedPreferences.getInt("name", 0);

        List<String> xLabels = new ArrayList<>(Arrays.asList("2months", "4months", "6months", "8months", "10months", "12months", "14months", "16months", "18months", "20months", "22months", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"));

        ArrayList<Head> heads = new ArrayList<>();
        ArrayList<Head> other = new ArrayList<>();
        Head head = new Head(45, 2);
        Head head_two = new Head(50, 3);
        Head head_three = new Head(63, 4);
        Head head4 = new Head(64, 5);
        Head head5 = new Head(44, 6);
        Head head6 = new Head(52, 7);
        Head head7 = new Head(78, 8);
        Head head1 = new Head(78, 9);
        Head head2 = new Head(80, 10);
        Head head3 = new Head(75, 11);
        Head head8 = new Head(99, 12);
        heads.add(head);
        heads.add(head_two);
        heads.add(head_three);
        heads.add(head4);
        heads.add(head5);
        heads.add(head6);
        heads.add(head7);
        heads.add(head1);
        heads.add(head2);
        heads.add(head3);
        heads.add(head8);

        List<com.github.mikephil.charting.data.Entry> entries = new ArrayList<>();
        for(Head h: heads){
            entries.add(new com.github.mikephil.charting.data.Entry(h.getChildAge(), h.getHeadSize()));
        }


        head8.setChildAge(2);
        head3.setChildAge(3);
        head2.setChildAge(4);
        head1.setChildAge(5);
        head7.setChildAge(6);
        head6.setChildAge(7);
        head5.setChildAge(8);
        head4.setChildAge(9);
        head_three.setChildAge(10);
        head_two.setChildAge(11);
        head.setChildAge(12);


        other.add(head8);
        other.add(head3);
        other.add(head2);
        other.add(head1);
        other.add(head7);
        other.add(head6);
        other.add(head5);
        other.add(head4);
        other.add(head_three);
        other.add(head_two);
        other.add(head);


        List<Entry> newest = new ArrayList<>();
        for(Head h: other){
            newest.add(new Entry(h.getChildAge(), h.getHeadSize()));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Your Child"); // add entries to dataset
        dataSet.setColor(Color.RED);
        dataSet.setValueTextColor(Color.BLACK); // styling, ...

        LineDataSet set = new LineDataSet(newest, "Average");
        set.setColor(Color.BLACK);
        dataSet.setValueTextColor(Color.RED);

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);
        dataSets.add(set);

        LineData lineData = new LineData(dataSets);
        lineChart.setData(lineData);
        lineChart.setDragEnabled(true);
        lineChart.invalidate(); // refresh

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(29);
        xAxis.setGranularity(1);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.setVisibleXRangeMaximum(5);
        lineChart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xLabels));
        //lineChart.setVisibleYRangeMaximum(50, YAxis.AxisDependency.LEFT);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChartActivity.this, MedicalActivity.class);
                startActivity(intent);
            }
        });
    }
}