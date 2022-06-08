package com.myproj.wear.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.myproj.wear.helperclasses.PatientHelperClass;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SmsLimitHelperDb extends SQLiteOpenHelper {

    public static final String DATABASE_NAME= "SMSLimit.db";
    public static final String TABLE_NAME= "Sms_info";
    public static final String COL_1= "COUNT";
    public static final String COL_2= "DATE";
    public static final String COL_3= "USERNAME";

    public SmsLimitHelperDb(Context context) {
        super(context, DATABASE_NAME, null, 1 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +"(COUNT INTEGER, DATE TEXT, USERNAME TEXT)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String date, String username){

        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_1,0);
        contentValues.put(COL_2,date);
        contentValues.put(COL_3,username);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }


    public void updateSmsCount(String date, String userName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Integer count = 0;
        if(!Objects.isNull(getCount(userName))) {
             count = (Integer) getCount(userName).get("COUNT") + 1;
            Log.d("COUNT","count"+count);
            db.execSQL("UPDATE Sms_info SET DATE =? WHERE USERNAME=?", new String[]{date, userName});
            Log.d("HEALTH CHECK","cCHECKINH----");
            db.execSQL("UPDATE Sms_info SET COUNT =? WHERE USERNAME=?",new Object[]{count,userName});
        }
        else {
            insertData(date,userName);
        }
    }

    public Map<String,Object> getCount(String userName) {
        Map<String,Object> countMap = null;
        Integer count = 0;
        String date = "";
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM Sms_info WHERE USERNAME=?", new String[]{userName});
                countMap = new HashMap<>();
                Integer countIndex = cursor.getColumnIndex("COUNT");
                Integer dateIndex = cursor.getColumnIndex(COL_2);
                while (cursor.moveToNext()) {
                    count = cursor.getInt(countIndex);
                    date = cursor.getString(dateIndex);
                }
                countMap.put("COUNT", count);
                countMap.put("DATE", date);
            Log.d("COUNTMAP","countMap"+countMap);


        }
        catch(Exception e){
            return null;
        }
        return countMap;
    }


    public void deleteHealthData(String userName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM Sms_info WHERE USERNAME=?",new String[]{userName});
    }
}
