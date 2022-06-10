package com.myproj.wear.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.myproj.wear.R;
import com.myproj.wear.caretaker.CaretakerHome;
import com.myproj.wear.common.LoginSignup.StartUpScreen;
import com.myproj.wear.databases.LoginDb;
import com.myproj.wear.databases.PatientDb;

import java.util.Objects;

public class PatientLogin extends AppCompatActivity {

    Button callSignUp,loginBtn;
    TextInputLayout username,password;
    LoginDb dbHelper;
    PatientDb patientDb;
    RadioGroup user_radio;
    RadioButton user_select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_patient_login);

        callSignUp = findViewById(R.id.signPage);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.login);
        user_radio = findViewById(R.id.user_radio);
        dbHelper = new LoginDb(this);
        patientDb = new PatientDb(this);

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
                int id = user_radio.getCheckedRadioButtonId();                  // user info caretaker or patient
                user_select = findViewById(id);
                String user = "";
                if(Objects.nonNull(user_select)) {
                    user = user_select.getText().toString();
                }
                if (uname.isEmpty() || psw.isEmpty() ){
                    Toast.makeText(PatientLogin.this, "Please enter data", Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean result = dbHelper.checkUsernamePassword(uname,psw);
                    Log.d("LOGIN","result"+result);
                    boolean patient = false;
                    if("Patient".equals(user) && result &&  patientDb.patientOrNot(uname)) {
                        dbHelper.updateActiveUser("T",uname);
                        Intent i=new Intent(getApplicationContext(),HomePagePatient.class);
                        i.putExtra("patientName", uname);
                        startActivity(i);
                        finish();
                    }
                    else if("Caretaker".equals(user) && result) {
                        String patientName = patientDb.getPatiantInfo(uname);
                        Log.d("PATIENTNAME","patientName"+patientName);
                        Intent i = new Intent(getApplicationContext(), CaretakerHome.class);
                        i.putExtra("patientName", patientName);
                        startActivity(i);
                        finish();
                    }
                    else {
                        Toast.makeText(PatientLogin.this, "Invalid Username / Password/Invalid Patient", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void backToStartUp(View view){
        Intent i = new Intent(getApplicationContext(), StartUpScreen.class);
        startActivity(i);
        finish();
    }

}