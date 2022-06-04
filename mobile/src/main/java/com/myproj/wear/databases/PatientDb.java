package com.myproj.wear.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.myproj.wear.helperclasses.PatientHelperClass;

public class PatientDb extends SQLiteOpenHelper {
        public static final String DATABASE_NAME= "PatientData.db";
        public static final String TABLE_NAME= "Patient_table";
        public static final String COL_1= "NAME";
        public static final String COL_2= "EMAIL";
        public static final String COL_3= "NUMBER";
        public static final String COL_4= "PASSWORD";
        public static final String COL_5= "GENDER";
        public static final String COL_6= "DOB";

        public PatientDb(Context context) {
            super(context, DATABASE_NAME, null, 1 );
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table " + TABLE_NAME +"(NAME TEXT ,EMAIL TEXT ,NUMBER INTEGER PRIMARY KEY,PASSWORD TEXT ,GENDER TEXT ,DOB DATE )" );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

        public boolean insertData(PatientHelperClass patientInfo){
            String name = patientInfo.getUsername();
            String email = patientInfo.getEmail();
            String number = patientInfo.getPhoneNo();
            String password = patientInfo.getPassword();
            String gender = patientInfo.getGender();
            String dob = patientInfo.getDate_of_birth();
            SQLiteDatabase db= this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_1,name);
            contentValues.put(COL_2,email);
            contentValues.put(COL_3,number);
            contentValues.put(COL_4,password);
            contentValues.put(COL_5,gender);
            contentValues.put(COL_6,dob);
            long result = db.insert(TABLE_NAME,null,contentValues);
            if (result == -1)
                return false;
            else
                return true;
        }

        //checking the uname and password
        public Boolean checkUsernamePassword(String username,String password){
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM  Patient_table   WHERE  NAME =? AND  PASSWORD =?",new String[] {username,password});
            if (cursor.getCount()>0) {
                return true;
            }
            else {
                return false;
            }
        }

    }
