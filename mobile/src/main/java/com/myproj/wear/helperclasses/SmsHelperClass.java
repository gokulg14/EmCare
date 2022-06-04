package com.myproj.wear.helperclasses;

import android.database.Cursor;
import android.telephony.SmsManager;

import com.myproj.wear.databases.EmNumDb;

import java.util.ArrayList;
import java.util.List;

public class SmsHelperClass {

    List<String> getNumbers = new ArrayList<>();
    String message = "Help me";
    EmNumDb numDb;

    public SmsHelperClass() {
        Cursor cursor = numDb.GetNum();
        if (cursor.getCount()>0){
            while (cursor.moveToNext()){
                String num = cursor.getString(1);
                getNumbers.add(num);
            }
        }
        else
            return;
    }

    public void sendSMS () {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(getNumbers.get(0),null,message,null,null);
        smsManager.sendTextMessage(getNumbers.get(1),null,message,null,null);
        smsManager.sendTextMessage(getNumbers.get(2),null,message,null,null);
    }

}
