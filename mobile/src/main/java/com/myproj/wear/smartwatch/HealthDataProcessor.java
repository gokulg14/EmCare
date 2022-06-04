package com.myproj.wear.smartwatch;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproj.wear.helperclasses.SmsHelperClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.Set;

public class HealthDataProcessor {

    SmsHelperClass smsHelperClass;

    private final String PRESSURE_KEY = "android.sensor.pressure";

    private final String GRAVITY_KEY ="android.sensor.accelerometer";

    private final String HEART_BEAT = "android.sensor.heart_rate";


    public void processHealthInfo(String message) {
        Log.d("MESSAGE", "message recieved: "+message);
        try {
            Map<String, List<String>> healthdata = new ObjectMapper().readValue(message, HashMap.class);
            List<String> pressureData = (List<String>) healthdata.get(PRESSURE_KEY);
            List<String> gravityData = (List<String>) healthdata.get(GRAVITY_KEY);
            List<String> heartBeatData = (List<String>) healthdata.get(HEART_BEAT);

            Log.d("INPUT","pressureData"+pressureData);


            Double averagePressure = Objects.nonNull(pressureData)?pressureData.stream().mapToDouble(pressure->Double.parseDouble(pressure)).average().getAsDouble():0;
            Double averageGravity =  Objects.nonNull(gravityData)?gravityData.stream().mapToDouble(pressure->Double.parseDouble(pressure)).average().getAsDouble():0;
            Double averageHeartdata =  Objects.nonNull(heartBeatData)?heartBeatData.stream().mapToDouble(pressure->Double.parseDouble(pressure)).average().getAsDouble():0;

            Log.d("INPUT","averagePressure"+averagePressure);
            Log.d("INPUT","averageGravity"+averageGravity);
            Log.d("INPUT","averageHeartdata"+averageHeartdata);



            if(averagePressure>140
                    || averageGravity>0.1
                    || averageGravity<-0.1
                    || averageHeartdata>100
                    || averageHeartdata<40) {
                //Rest of the code should come here
                smsHelperClass = new SmsHelperClass();
                smsHelperClass.sendSMS();
                System.out.println("person health is not well or he is going to fall");
            }

        }
        catch(Exception e) {
            Log.d("FAILURE", "processHealthInfo() returned: " + e.getMessage() );
        }
    }
}
