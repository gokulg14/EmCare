package com.myproj.wear.caretaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.myproj.wear.R;
import com.myproj.wear.databases.HealthDataDb;
import com.myproj.wear.helperclasses.HealthDataHelper;

import java.util.ArrayList;
import java.util.List;

public class CaretakerHome extends AppCompatActivity {


    private String username;

    List<HealthDataHelper> healthDataHelper = new ArrayList<>(50);

    HealthDataDb healthDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caretaker_home);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Intent i = getIntent();
        username = i.getStringExtra("patientName");

        healthDb = new HealthDataDb(this);

        insertingData();
    }


    private void insertingData(){

        TableLayout stk = (TableLayout) findViewById(R.id.health_data_table);
        TableRow tbrow0 = new TableRow(this);

        TextView tv0 = new TextView(this);
        tv0.setText(" Name ");
        tv0.setTextColor(Color.BLACK);
        tbrow0.addView(tv0);

        TextView tv1 = new TextView(this);
        tv1.setText(" Heart Rate ");
        tv1.setTextColor(Color.BLACK);
        tbrow0.addView(tv1);

        TextView tv2 = new TextView(this);
        tv2.setText(" Blood Pressure ");
        tv2.setTextColor(Color.BLACK);
        tbrow0.addView(tv2);

        TextView tv3 = new TextView(this);
        tv3.setText(" Motion ");
        tv3.setTextColor(Color.BLACK);
        tbrow0.addView(tv3);

        TextView tv4 = new TextView(this);
        tv4.setText(" Time ");
        tv4.setTextColor(Color.BLACK);
        tbrow0.addView(tv4);

        stk.addView(tbrow0);

        healthDataHelper = healthDb.getHealthData(username);
        Log.d("HEALTHDATA","healthDataHelper"+healthDataHelper);
        HealthDataHelper healthDataDto = new HealthDataHelper();
        for(HealthDataHelper healthdata: healthDataHelper) {

            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setText(healthdata.getNamew());
            t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);

            TextView t2v = new TextView(this);
            t2v.setText(healthdata.getHeartRateReading());
            t2v.setTextColor(Color.BLACK);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);

            TextView t3v = new TextView(this);
            t3v.setText(healthdata.getBpReading());
            t3v.setTextColor(Color.BLACK);
            t3v.setGravity(Gravity.CENTER);
            tbrow.addView(t3v);

            TextView t4v = new TextView(this);
            t4v.setText(healthdata.getMotionSensorReading());
            t4v.setTextColor(Color.BLACK);
            t4v.setGravity(Gravity.CENTER);
            tbrow.addView(t4v);

            TextView t5v = new TextView(this);
            t5v.setText(healthdata.getDate());
            t5v.setTextColor(Color.BLACK);
            t5v.setGravity(Gravity.CENTER);
            tbrow.addView(t5v);

            stk.addView(tbrow);

        }

    }
}