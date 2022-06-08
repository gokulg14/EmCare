package com.myproj.wear.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.myproj.wear.R;
import com.myproj.wear.common.LoginSignup.StartUpScreen;
import com.myproj.wear.databases.HealthDataDb;
import com.myproj.wear.databases.LoginDb;
import com.myproj.wear.databases.PatientDb;
import com.myproj.wear.databases.SmsLimitHelperDb;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class UpdateOrDelUserInfo extends AppCompatActivity {

    Button update,delete;
    EditText addField,addDetails;
    String username;

    PatientDb patientDb;

    LoginDb loginDb;

    HealthDataDb healthdataDb;

    SmsLimitHelperDb smsLimitHelperDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_update_or_del_user_info);

        addField = findViewById(R.id.getData);
        addDetails = findViewById(R.id.getData1);

        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete_data);

        Intent i = getIntent();
        username = i.getStringExtra("profileName");

        patientDb = new PatientDb(this);
        loginDb = new LoginDb(this);
        smsLimitHelperDb = new SmsLimitHelperDb(this);
        healthdataDb = new HealthDataDb(this);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             if(addField!=null && addField.getText().toString().toLowerCase().equals("caretaker") && addDetails!=null){
                String careTakerName =  addDetails.getText().toString().split("-")[0];
                 String careTakerPhonenumber = addDetails.getText().toString().split("-")[1];
                 patientDb.updateHealth(careTakerName,careTakerPhonenumber,"","",username);
             }
             else if (addField!=null && addField.getText().toString().toLowerCase().equals("patientemail") && addDetails!=null) {
                 patientDb.updateHealth("","",addDetails.getText().toString(),"",username);

             }
             else if((addField!=null && addField.getText().toString().toLowerCase().equals("patientnumber") && addDetails!=null)) {
                    patientDb.updateHealth("","","",addDetails.getText().toString(),username);

                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    patientDb.deletePatient(username);
                    loginDb.deleteHealthData(username);
                    healthdataDb.deleteHealthData(username);
                    smsLimitHelperDb.deleteHealthData(username);
                    Intent i = new Intent(getApplicationContext(), StartUpScreen.class);
                     startActivity(i);

            }
        });

    }
}