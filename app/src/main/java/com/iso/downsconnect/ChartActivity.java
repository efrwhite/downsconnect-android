package com.iso.downsconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.iso.downsconnect.helpers.DBHelper;
import com.iso.downsconnect.objects.MedicalInfo;
import com.iso.downsconnect.objects.Point;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//Activity used to display charts for height, weight, and head circumference
public class ChartActivity extends AppCompatActivity {
    private DBHelper helper;
    private ArrayList<Point> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        //declare and intialize layout objects
        LineChart lineChart = (LineChart) findViewById(R.id.growth_chart);
        Button back = findViewById(R.id.backButton);


        //get current child ID
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final int childID = sharedPreferences.getInt("name", 1);

        //determine which chart was clicked
        String chartType = getIntent().getStringExtra("chart");
        Log.i("chid", String.valueOf(childID));

        //initialize db object
        helper = new DBHelper(this);

        //get the data from the db depending on which kind of chart you are looking at
        if(chartType.equals("Height")){
            data = helper.getHeight(childID);
        }
        else if(chartType.equals("Weight")){
            data = helper.getWeight(childID);
        }
        else{
            data = helper.getHeadSizes(childID);
        }

        //create labels for x-axis
        List<String> xLabels = new ArrayList<>(Arrays.asList("2months", "4months", "6months", "8months", "10months", "12months", "14months", "16months",
                "18months", "20months", "22months", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"));

//        ArrayList<Point> points = new ArrayList<>();
//        if(data.size() != 0){
//            int i = 0;
//            for(Float ints: data){
//                Point point = new Point(i, ints);
//                points.add(point);
//                i++;
//            }
//        }
//        ArrayList<Point> other = new ArrayList<>();
//        Point point = new Point(2, 55);
//        Point point_two = new Point(3, 65);
//        Point point_three = new Point( 4, 88);
//        Point point4 = new Point( 5, 46);
//        Point point5 = new Point( 6, 12);
//        Point point6 = new Point( 7, 55);
//        Point point7 = new Point( 8, 98);
//        Point point1 = new Point( 9, 67);
//        Point point2 = new Point( 10, 32);
//        Point point3 = new Point( 11, 67);
//        Point point8 = new Point( 12, 89);
//        points.add(point);
//        points.add(point_two);
//        points.add(point_three);
//        points.add(point4);
//        points.add(point5);
//        points.add(point6);
//        points.add(point7);
//        points.add(point1);
//        points.add(point2);
//        points.add(point3);
//        points.add(point8);

        //create a list containing the coordinates for each point
        List<com.github.mikephil.charting.data.Entry> entries = new ArrayList<>();
        for(Point h: data){
            entries.add(new com.github.mikephil.charting.data.Entry(h.getX(), h.getY()));
        }


//        point8.setX(2);
//        point3.setX(3);
//        point2.setX(4);
//        point1.setX(5);
//        point7.setX(6);
//        point6.setX(7);
//        point5.setX(8);
//        point4.setX(9);
//        point_three.setX(10);
//        point_two.setX(11);
//        point.setX(12);
//
//
//        other.add(point8);
//        other.add(point3);
//        other.add(point2);
//        other.add(point1);
//        other.add(point7);
//        other.add(point6);
//        other.add(point5);
//        other.add(point4);
//        other.add(point_three);
//        other.add(point_two);
//        other.add(point);


//        List<Entry> newest = new ArrayList<>();
//        for(Point h: other){
//            newest.add(new Entry(h.getX(), h.getY()));
//        }

        LineDataSet dataSet = new LineDataSet(entries, "Your Child"); // add entries to dataset
        //define the color for the dataset
        dataSet.setColor(Color.RED);
        dataSet.setValueTextColor(Color.BLACK); // styling, ...

        //LineDataSet set = new LineDataSet(newest, "Average");
        //set.setColor(Color.BLACK);
//        dataSet.setValueTextColor(Color.RED);

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);
        //dataSets.add(set);

        LineData lineData = new LineData(dataSets);
        lineChart.setData(lineData);
        lineChart.setDragEnabled(true);
        lineChart.invalidate(); // refresh

        //define spacing and display info for x axis
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(29);
        xAxis.setGranularity(1);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.setVisibleXRangeMaximum(5);
        lineChart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xLabels));
        //lineChart.setVisibleYRangeMaximum(50, YAxis.AxisDependency.LEFT);

        //button for navigating back to home page
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChartActivity.this, MedicalActivity.class);
                startActivity(intent);
            }
        });
    }
}