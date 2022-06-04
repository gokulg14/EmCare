package com.myproj.wear.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import com.google.android.material.textfield.TextInputLayout;
import com.myproj.wear.R;

public class UserProfile extends AppCompatActivity {
    
    private TextInputLayout uName,uEmail,uNumber,uPassword;

    private String name,email,number,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_profile);
        
        uName = findViewById(R.id.patient_name);
        uEmail = findViewById(R.id.patient_email);
        uNumber = findViewById(R.id.patient_phoneNo);
        uPassword = findViewById(R.id.patient_password);


    }
}