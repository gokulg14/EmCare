package com.myproj.wear.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.myproj.wear.R;
import com.myproj.wear.databases.PatientDb;

public class PatientLogin extends AppCompatActivity {

    Button callSignUp,loginBtn;
    TextInputLayout username,password;
    PatientDb dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_patient_login);

        callSignUp = findViewById(R.id.signPage);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.login);
        dbHelper = new PatientDb(this);

        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PatientLogin.this,PatientSignUp.class);
                startActivity(i);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = username.getEditText().getText().toString().trim();
                String psw = password.getEditText().getText().toString().trim();

                if (uname.isEmpty() | psw.isEmpty()){
                    Toast.makeText(PatientLogin.this, "Please enter data", Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean result = dbHelper.checkUsernamePassword(uname,psw);
                    if (result == true){
                        Intent i=new Intent(getApplicationContext(),HomePagePatient.class);
                        startActivity(i);
                        finish();
                    }
                    else {
                        username.setError("Incorrect Username");
                        password.setError("Incorrect password");
                        Toast.makeText(PatientLogin.this, "Invalid Username / Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}