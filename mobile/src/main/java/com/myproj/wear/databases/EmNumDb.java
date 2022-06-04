package com.myproj.wear.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.myproj.wear.helperclasses.EmNumberHelper;

public class EmNumDb extends SQLiteOpenHelper {

    public static final String DATABASE_NAME= "emNUm.db";
    public static final String TABLE_NAME= "Num_table";
    public static final String COL_1= "Name";
    public static final String COL_2= "Number";

    public EmNumDb(Context context) {super(context, DATABASE_NAME, null, 1);}

    @Override
    public void onCreate(SQLiteDatabase db1){
        db1.execSQL("create table " + TABLE_NAME + " (Name TEXT PRIMARY KEY, Number INTEGER) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db1, int oldVersion, int newVersion) {
        db1.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db1);
    }

    public Boolean insertNum(EmNumberHelper numberInfo){
        String name = numberInfo.getName();
        String number = numberInfo.getNumber();
        SQLiteDatabase db1=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,name);
        contentValues.put(COL_2,number);
        long result = db1.insert(TABLE_NAME, null,contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    public Cursor GetNum() {
        SQLiteDatabase db1=this.getReadableDatabase();
        Cursor cursor = db1.rawQuery("SELECT * FROM " + TABLE_NAME,null);
        return cursor;
    }
}
