package com.myproj.wear.smartwatch;

import android.app.Activity;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproj.wear.databases.EmNumDb;
import com.myproj.wear.databases.HealthDataDb;
import com.myproj.wear.databases.LoginDb;
import com.myproj.wear.databases.PatientDb;
import com.myproj.wear.helperclasses.ApplicationContextProvider;
import com.myproj.wear.helperclasses.EmNumberHelper;
import com.myproj.wear.helperclasses.HealthDataHelper;
import com.myproj.wear.helperclasses.SmsHelperClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.Set;

public class HealthDataProcessor extends Activity {

    SmsHelperClass smsHelperClass=new SmsHelperClass();

    private final String PRESSURE_KEY = "android.sensor.pressure";

    private final String GRAVITY_KEY ="android.sensor.accelerometer";

    private final String HEART_BEAT = "android.sensor.heart_rate";

    LoginDb loginDb = new LoginDb(ApplicationContextProvider.getContext() );
    HealthDataDb healthDataDb = new HealthDataDb(ApplicationContextProvider.getContext());
    EmNumDb emnumDb = new EmNumDb(ApplicationContextProvider.getContext());
    PatientDb patientDb = new PatientDb(ApplicationContextProvider.getContext());


    public void processHealthInfo(String message) {
        Log.d("MESSAGE", "message recieved: "+message);
        try {
            Map<String, List<String>> healthdata = new ObjectMapper().readValue(message, HashMap.class);
            List<String> pressureData = (List<String>) healthdata.get(PRESSURE_KEY);
            List<String> gravityData = (List<String>) healthdata.get(GRAVITY_KEY);
            List<String> heartBeatData = (List<String>) healthdata.get(HEART_BEAT);
            List<String> timeData = (List<String>)healthdata.get("TIME");
            Log.d("INPUT","pressureData"+pressureData);


            Double averagePressure = Objects.nonNull(pressureData)?pressureData.stream().mapToDouble(pressure->Double.parseDouble(pressure)).average().getAsDouble():0;
            Double averageGravity =  Objects.nonNull(gravityData)?gravityData.stream().mapToDouble(pressure->Double.parseDouble(pressure)).average().getAsDouble():0;
            Double averageHeartdata =  Objects.nonNull(heartBeatData)?heartBeatData.stream().mapToDouble(pressure->Double.parseDouble(pressure)).average().getAsDouble():72;

            Log.d("INPUT","averagePressure"+averagePressure);
            Log.d("INPUT","averageGravity"+averageGravity);
            Log.d("INPUT","averageHeartdata"+averageHeartdata);



            if(averagePressure>140
                    || averageGravity>0.1
                    || averageGravity<-0.1
                    || averageHeartdata>100
                    || averageHeartdata<40) {
                //Rest of the code should come here
                List<EmNumberHelper> emNumberHelpers = emnumDb.GetNum();
                Log.d("OUTPUT","emNumberHelpers"+emNumberHelpers);
                String activeUserStatus = loginDb.getActiveUserStatus();
                Log.d("LOGINUSER","activeUserStatus"+activeUserStatus);
                String operationStatus =  "NOACTIVEUSER".equals(activeUserStatus)?
                        smsHelperClass.sendSMS(emNumberHelpers.get(0).getNumber(),emNumberHelpers.get(1).getNumber(),emNumberHelpers.get(2).getNumber()," Emergency please help The person is weak ",activeUserStatus)
                        :healthDataUpdation(activeUserStatus,averageHeartdata,averageGravity,averagePressure,timeData.get(0));
                Log.d("OUTPUT","operationStatus"+operationStatus);
            }


        }
        catch(Exception e) {
            Log.d("FAILURE", "processHealthInfo() returned: " + e.getMessage() );
        }


    }
    public String healthDataUpdation(String userName,Double heartdata, Double gravity, Double pressure,String time) {
        String heartData = String.valueOf(heartdata);
        String gravityData = String.valueOf(gravity);
        String pressureData = String.valueOf(pressure);
        SmsHelperClass smsHelperClass = new SmsHelperClass();
        String careTakerPhoneNumber = patientDb.getCareTakerPhoneNumber(userName);
        Log.d("CARETAKER", "careTakerName" + careTakerPhoneNumber);
        smsHelperClass.sendSMSEmergencyCareTaker(careTakerPhoneNumber,"The Patient" + userName + "Need Help, His condition is bad",userName);
        boolean healthDataInserted = false;
        if (!healthDataDb.checkDuplicate(userName,heartData,gravityData,pressureData)) {
        healthDataInserted = healthDataDb.insertData(userName, heartData,pressureData, gravityData, time);
        }
         Log.d("OUTPUT","healthDataInserted"+healthDataInserted);
         return "SUCCESS";
    }

}
