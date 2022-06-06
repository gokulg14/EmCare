package com.myproj.wear.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.myproj.wear.helperclasses.PatientHelperClass;

public class LoginDb extends SQLiteOpenHelper {

    public static final String DATABASE_NAME= "Login.db";
    public static final String TABLE_NAME= "Patient_Login_table";
    public static final String COL_1= "NAME";
    public static final String COL_2= "PASSWORD";
    public static final String COL_3= "ACTIVE";

    public LoginDb(Context context) {
        super(context, DATABASE_NAME, null, 1 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +"(NAME TEXT PRIMARY KEY, PASSWORD TEXT, ACTIVE TEXT)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(PatientHelperClass patientInfo){
        String name = patientInfo.getUsername();
        String password = patientInfo.getPassword();
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_1,name);
        contentValues.put(COL_2,password);
        contentValues.put(COL_3,patientInfo.getActive());
        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }


    //checking the uname and password
    public Boolean checkUsernamePassword(String username,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM  Patient_Login_table   WHERE  NAME =? AND  PASSWORD =?",new String[] {username,password});
        if (cursor.getCount()>0) {
            return true;
        }
        else {
            return false;
        }
    }

    public String getActiveUserStatus() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Patient_Login_table WHERE ACTIVE = 'T'",null);
        int nameIndex = cursor.getColumnIndex(COL_1);
        while(cursor.moveToNext()) {
            return cursor.getString(nameIndex);
        }
       return "NO_ACTIVE_USER";
    }

    public void updateActiveUser(String status,String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE Patient_Login_table SET ACTIVE = ? WHERE NAME=?" ,new String[]{status,username});
    }
}
