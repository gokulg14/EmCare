package com.myproj.wear.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HealthDataDb extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "UserHealthData.db";
    public static final String TABLE_NAME = "Health_table";
    public static final String COL_1 = "Id";
    public static final String COL_2 = "Heart_Rate";
    public static final String COL_3 = "BP_Data";
    public static final String COL_4 = "Motion_Sensor";
    public static final String COL_5 = "Fall_Detection";
    public static final String COL_6 = "Time";

    public HealthDataDb(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(Id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT , Heart_Rate INTEGER NOT NULL ," +
                "BP_Data INTEGER NOT NULL,Motion_Sensor INTEGER NOT NULL,Fall_Detection Text NOT NULL,Time DATE NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String heartRate, String bp, String motionS, String fallDetect, Float time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, heartRate);
        contentValues.put(COL_3, bp);
        contentValues.put(COL_4, motionS);
        contentValues.put(COL_5, fallDetect);
        contentValues.put(COL_6, time);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
}
