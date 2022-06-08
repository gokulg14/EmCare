package com.myproj.wear.helperclasses;

import android.database.Cursor;
import android.telephony.SmsManager;
import android.util.Log;

import com.myproj.wear.databases.EmNumDb;
import com.myproj.wear.databases.LoginDb;
import com.myproj.wear.databases.SmsLimitHelperDb;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SmsHelperClass {


   SmsLimitHelperDb smslimit = new SmsLimitHelperDb(ApplicationContextProvider.getContext());

    public String sendSMS (String phoneNumber1, String phoneNumber2, String phoneNumber3,String message,String loginUser) {
        if(SMScountValidation(loginUser)) {
            SmsManager smsManager = SmsManager.getDefault();
            //smsManager.sendTextMessage(phoneNumber1,null,message,null,null);
           // smsManager.sendTextMessage(phoneNumber2,null,message,null,null);
            //smsManager.sendTextMessage(phoneNumber3,null,message,null,null);
            Log.d("SMS SENDING","SMS TO EMERGENCY");
            Log.d("EmNumber",message +" send to number " +phoneNumber1);
            Log.d("EmNumber",message +" send to number " +phoneNumber2);
            Log.d("EmNumber",message +" send to number " +phoneNumber3);
            String currentDate = LocalDateTime.now().toString();
            smslimit.updateSmsCount(currentDate,loginUser);
        }
        System.out.println(phoneNumber1);
        return "SUCCESS";
    }


    public void sendSmsToCareTaker(String phoneNumber,String message,String loginUser) {

            SmsManager smsManager = SmsManager.getDefault();
            System.out.println("SMS message to care taker" + message);
            smsManager.sendTextMessage(phoneNumber,null,message,null,null);
            String currentDate = LocalDateTime.now().toString();
            smslimit.insertData(currentDate,loginUser);

    }

    public boolean SMScountValidation(String userName) {
        String currentDate = LocalDateTime.now().toString();
        Integer currentDay= LocalDateTime.now().getDayOfMonth();
       Map<String,Object> logindata =  smslimit.getCount(userName);
       if(Objects.isNull(logindata)) {
           return true;
       }
       String dbdate = (String) logindata.get("DATE");
       if(StringUtils.isEmpty(dbdate)){
           dbdate = currentDate;
       }
       Integer dbday =  LocalDateTime.parse(dbdate).getDayOfMonth();
       Integer count = (Integer) logindata.get("COUNT");
       if(currentDay==dbday && count<=4) {
           return true;
       }
      return false;
    }

    public boolean updateCaretaker(String patieNTnAME , String username,String phoneNumber,LoginDb loginDb) {              //????boolean
        PatientHelperClass careTaker = new PatientHelperClass();
        careTaker.setUsername(username);
        String passwordCareTaker = RandomStringUtils.randomAlphabetic(8);
        careTaker.setPassword(passwordCareTaker);
        boolean careTakerAdded = false;
        if(loginDb.insertData(careTaker)) {
            careTakerAdded = true;
            StringBuilder sb = new StringBuilder();
            sb.append(patieNTnAME);
            sb.append(" is selected you as a care taker ");
            sb.append(" please try to login with below credentials ");
            sb.append(" UserName:");
            sb.append(username);
            sb.append(",");
            sb.append(" ");
            sb.append("PassWord :");
            sb.append(passwordCareTaker);
            sendSmsToCareTaker(phoneNumber,sb.toString(),patieNTnAME);
        }
        return careTakerAdded;
    }
}
