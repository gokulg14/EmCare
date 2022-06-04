package com.myproj.wear.patient;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.myproj.wear.R;
import com.myproj.wear.databases.PatientDb;
import com.myproj.wear.helperclasses.PatientHelperClass;



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
    String username,password,email,number;

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

        myDb = new PatientDb(this);  // Constructor is called and database is created

        // getting data from previous activity through intent
        Intent i = getIntent();
        username = i.getStringExtra("username");
        email = i.getStringExtra("email");
        number = i.getStringExtra("number");
        password = i.getStringExtra("password");           // data obtained with their respective key


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    addDataToDatabase();
            }
        });
    }

    public void backToPreviousSignUp(View view){
        Intent i = new Intent(getApplicationContext(),PatientSignUp.class);
        startActivity(i);
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
        patientInfo.setDate_of_birth(date_of_birth);
        boolean isInserted = myDb.insertData(patientInfo);
        if (isInserted = true)
            Toast.makeText(SignUp2nd.this, "Data Inserted", Toast.LENGTH_LONG).show();
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