package com.myproj.wear.helperclasses;

import android.database.Cursor;
import android.telephony.SmsManager;

import com.myproj.wear.databases.EmNumDb;

import java.util.ArrayList;
import java.util.List;

public class SmsHelperClass {





    public String sendSMS (String phoneNumber1, String phoneNumber2, String phoneNumber3,String message) {
        //SmsManager smsManager = SmsManager.getDefault();
       // smsManager.sendTextMessage(phoneNumber1,null,message,null,null);
       // smsManager.sendTextMessage(phoneNumber1,null,message,null,null);
        //smsManager.sendTextMessage(phoneNumber1,null,message,null,null);
        System.out.println(phoneNumber1);
        return "SUCCESS";
    }


    public void sendSmsToCareTaker(String phoneNumber,String message) {
        //SmsManager smsManager = SmsManager.getDefault();
        System.out.println("SMS message to care taker"+message);
       // smsManager.sendTextMessage(phoneNumber,null,message,null,null);
    }
}
