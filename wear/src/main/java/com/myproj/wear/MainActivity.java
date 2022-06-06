package com.myproj.wear;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.myproj.wear.databinding.ActivityMainBinding;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class MainActivity extends Activity {

    private TextView bloodPressure;
    private ActivityMainBinding binding;
    private SensorManager mSensorManager;
    HashMap<String, Set<String>> sensorData = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        setContentView(binding.getRoot());

        bloodPressure = binding.textView3;
        bloodPressure = (TextView) findViewById(R.id.textView3);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE), 500);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 50000);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_BEAT), 500);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mSensorListener);
    }

    public  HashMap<String, Set<String>> genereateHealthData(String type, SensorEvent event) {
        if(sensorData.containsKey(type)){
            sensorData.get(type).add(String.valueOf(event.values[0]));
        }
        else{
            Set<String> sensorvalues = new HashSet<>();
            sensorvalues.add(String.valueOf(event.values[0]));
            sensorData.put(type,sensorvalues);
        }
        Set<String> time = new HashSet<>();
        time.add(LocalDateTime.now().toString());
        sensorData.put("TIME", time);
        return sensorData;
    }


    private SensorEventListener mSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            String type = event.sensor.getStringType();
            JSONObject jsonOFhealth = new JSONObject(genereateHealthData(type,event));
            bloodPressure.setText(String.valueOf(event.values[0]));
            String datapath = "/my_path";
            new SendMessage(datapath,jsonOFhealth.toString()).start();
            System.out.println(sensorData);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            //Nothing to do
        }
    };

    //Send Message Implementation
    class SendMessage extends Thread {
        String path;
        String message;

//Constructor///

        SendMessage(String p, String m) {
            path = p;
            message = m;
        }

//Send the message via the thread. This will send the message to all the currently-connected devices//

        public void run() {

//Get all the nodes//

            Task<List<Node>> nodeListTask =
                    Wearable.getNodeClient(getApplicationContext()).getConnectedNodes();
            try {

//Block on a task and get the result synchronously//

                List<Node> nodes = Tasks.await(nodeListTask);

//Send the message to each device//
                for (Node node : nodes) {
                    Task<Integer> sendMessageTask =
                            Wearable.getMessageClient(MainActivity.this).sendMessage(node.getId(), path, message.getBytes());

                    try {

                        Integer result = Tasks.await(sendMessageTask);

//Handle the errors//

                    } catch (ExecutionException exception) {

//TO DO//

                    } catch (InterruptedException exception) {

//TO DO//

                    }

                }

            } catch (ExecutionException exception) {

//TO DO//

            } catch (InterruptedException exception) {

//TO DO//

            }
        }
    }
}