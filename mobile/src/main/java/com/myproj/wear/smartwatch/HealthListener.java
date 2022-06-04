package com.myproj.wear.smartwatch;

import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;
import com.myproj.wear.smartwatch.HealthDataProcessor;

public class HealthListener extends WearableListenerService {

    public static String SERVICE_CALLED_WEAR = "WearListClicked";

    HealthDataProcessor healthDataProcessor = new HealthDataProcessor();

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        if (messageEvent.getPath().equals("/my_path")) {

//...retrieve the message//

            final String message = new String(messageEvent.getData());
            Intent messageIntent = new Intent();
            messageIntent.setAction(Intent.ACTION_SEND);
            messageIntent.putExtra("message", message);

//Broadcast the received Data Layer messages locally//

            LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent);
            healthDataProcessor.processHealthInfo(message);
        }
        else {
            super.onMessageReceived(messageEvent);
        }
    }

}
