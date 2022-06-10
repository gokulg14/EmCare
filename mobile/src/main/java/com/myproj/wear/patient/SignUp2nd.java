package com.myproj.wear.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.myproj.wear.R;
import com.myproj.wear.databases.LoginDb;
import com.myproj.wear.databases.PatientDb;
import com.myproj.wear.helperclasses.PatientHelperClass;
import com.myproj.wear.helperclasses.SmsHelperClass;

import org.apache.commons.lang3.RandomStringUtils;


public class SignUp2nd extends AppCompatActivity {

    ImageView backBtn;
    Button next, login;
    RadioGroup gender_radio;
    RadioButton gender_select;
    String gender;
    DatePicker datePicker;
    String date_of_birth;
    PatientHelperClass patientInfo;
    PatientDb myDb;
    LoginDb loginDb;
    String username,password,email,number,careUsername,carePhoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up2nd);

        backBtn = findViewById(R.id.signup_back_btn);
        next = findViewById(R.id.reg_btn);
        login = findViewById(R.id.loginback);
        gender_radio = findViewById(R.id.gender_radio);
        datePicker = findViewById(R.id.user_date_of_birth);

        ActivityCompat.requestPermissions(SignUp2nd.this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);

        myDb = new PatientDb(this);  // Constructor is called and database is created
        loginDb = new LoginDb(this);
        // getting data from previous activity through intent
        Intent i = getIntent();
        username = i.getStringExtra("username");
        email = i.getStringExtra("email");
        number = i.getStringExtra("number");
        password = i.getStringExtra("password");           // data obtained with their respective key
        careUsername = i.getStringExtra("cUsername");           // data obtained with their respective key
        carePhoneNo = i.getStringExtra("cPhoneNo");           // data obtained with their respective key


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),PatientLogin.class);
                startActivity(i);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    addDataToDatabase();
            }
        });
    }

    //send data to db
    private void addDataToDatabase(){

        gender = getGender();
        date_of_birth = getDOB();

        patientInfo = new PatientHelperClass();
        patientInfo.setUsername(username);
        patientInfo.setEmail(email);
        patientInfo.setPhoneNo(number);
        patientInfo.setPassword(password);
        patientInfo.setGender(gender);
        patientInfo.setcTName(careUsername);
        patientInfo.setcTNum(carePhoneNo);
        patientInfo.setDate_of_birth(date_of_birth);
        boolean isPatientInserted = false;
        boolean loginInserted = false;
        try {
            isPatientInserted = myDb.insertData(patientInfo);
            loginInserted  = loginDb.insertData(patientInfo);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("isInserted"+isPatientInserted);
        System.out.println("loginInserted"+loginInserted);
        if (isPatientInserted = true) {
            Toast.makeText(SignUp2nd.this, "Data Inserted", Toast.LENGTH_LONG).show();
            new SmsHelperClass().updateCaretaker(username,careUsername,carePhoneNo,loginDb);
        }
    }

    private String getDOB(){

        StringBuilder builder = new StringBuilder();
        builder.append( (datePicker.getMonth() + 1)+"/"); //month is 0 based
        builder.append( datePicker.getDayOfMonth()+"/");
        builder.append( datePicker.getYear());

        return builder.toString();
    }

    private String getGender(){
        int id = gender_radio.getCheckedRadioButtonId();
        gender_select = findViewById(id);

        return gender_select.getText().toString();
    }

}