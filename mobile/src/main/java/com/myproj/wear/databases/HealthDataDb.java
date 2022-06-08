package com.myproj.wear.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.myproj.wear.helperclasses.HealthDataHelper;

import org.apache.commons.lang3.ObjectUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HealthDataDb extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "UserHealthData.db";
    public static final String TABLE_NAME = "Health_table";
    public static final String COL_1 = "Name";
    public static final String COL_2 = "Heart_Rate";
    public static final String COL_3 = "BP_Data";
    public static final String COL_4 = "Motion_Sensor";
    public static final String COL_6 = "Time";

    public HealthDataDb(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(Name TEXT, Heart_Rate INTEGER NOT NULL ," +
                "BP_Data INTEGER NOT NULL,Motion_Sensor INTEGER NOT NULl, Time TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name , String heartRate, String bp, String motionS, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, name);
        contentValues.put(COL_2, heartRate);
        contentValues.put(COL_3, bp);
        contentValues.put(COL_4, motionS);
        contentValues.put(COL_6, time);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;

    }


   public List<HealthDataHelper> getHealthData(String userName) {
        SQLiteDatabase db = this.getWritableDatabase();
       Cursor cursor =  db.rawQuery("SELECT * FROM Health_table where  NAME =?",new String[]{userName});
       int nameindex = cursor.getColumnIndex(COL_1);
       int heartrateindex = cursor.getColumnIndex(COL_2);
       int bpIndex = cursor.getColumnIndex(COL_3);
       int motionIndex = cursor.getColumnIndex(COL_4);
       int timeIndeX = cursor.getColumnIndex(COL_6);
       List<HealthDataHelper> healthData = new ArrayList<>(50);
       while(cursor.moveToNext()) {
           HealthDataHelper healthDataDto = new HealthDataHelper();
           healthDataDto.setNamew(cursor.getString(nameindex));
           healthDataDto.setBpReading(cursor.getString(bpIndex));
           healthDataDto.setHeartRateReading(cursor.getString(heartrateindex));
           healthDataDto.setMotionSensorReading(cursor.getString(motionIndex));
           healthDataDto.setDate(cursor.getString(timeIndeX));
           healthData.add(healthDataDto);

       }

       return healthData;

   }

   public boolean checkDuplicate(String userName,String heartdata, String gravity, String pressure) {
       SQLiteDatabase db = this.getWritableDatabase();
       Cursor cursor =  db.rawQuery("SELECT * FROM Health_table where  NAME =? AND Heart_Rate=? AND BP_Data=? AND Motion_Sensor=?",new String[]{userName,heartdata,pressure,gravity});
      if(cursor.getCount()>0) {
          return true;
      }
      return false;
    }

    public HealthDataHelper getLastUpdatedHealthData(String userName) {
        List<HealthDataHelper> healthdata = getHealthData(userName);
        Log.d("HEALTH DATA","healthdata"+healthdata);
        Map<LocalDateTime, HealthDataHelper> healthMap = new HashMap<>();
        List<LocalDateTime> dates = new ArrayList<>();
        if(Objects.nonNull(healthdata) && !healthdata.isEmpty()) {
            for (HealthDataHelper health : healthdata) {
                dates.add(LocalDateTime.parse(health.getDate()));
                healthMap.put(LocalDateTime.parse(health.getDate()), health);
            }
            if(Objects.nonNull(dates)) {
                return healthMap.get(Collections.max(dates));
            }
        }
        return new HealthDataHelper();
    }

    public void deleteHealthData(String userName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM Health_table WHERE NAME=?",new String[]{userName});
    }

}
