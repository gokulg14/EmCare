package com.myproj.wear.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.myproj.wear.R;
import com.myproj.wear.common.LoginSignup.StartUpScreen;
import com.myproj.wear.helperclasses.PatientHelperClass;


public class PatientSignUp extends AppCompatActivity {

    //for db fn

    private TextInputLayout regUsername, regEmail, regPhoneNo, regPassword, regConfPassword, regCareUsername, regCarePhoneNo;
    private Button next, backToLogin;
    ImageView back_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_patient_sign_up);

        // Hooks to all xml elements in signup page
        regUsername = findViewById(R.id.username);
        regEmail = findViewById(R.id.email);
        regPhoneNo = findViewById(R.id.phoneNo);
        regPassword = findViewById(R.id.password);
        regCarePhoneNo = findViewById(R.id.cTPhoneNo);
        regConfPassword = findViewById(R.id.confPassword);
        backToLogin = findViewById(R.id.loginback);
        regCareUsername = findViewById(R.id.care_username);
        next = findViewById(R.id.signup_next_btn);

        // back to the login page on a button click
        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PatientSignUp.this, PatientLogin.class);
                startActivity(i);
            }
        });

        // validation,passing data to next activity on a btn click
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //for passing data to next activity
                //Get all the values

                String username = regUsername.getEditText().getText().toString();
                String email = regEmail.getEditText().getText().toString();
                String phoneNo = regPhoneNo.getEditText().getText().toString();
                String password = regPassword.getEditText().getText().toString();
                String cname = regCareUsername.getEditText().getText().toString();
                String cPhoneNo = regCarePhoneNo.getEditText().getText().toString();

                //checking
                if (TextUtils.isEmpty(username) && TextUtils.isEmpty(email) && TextUtils.isEmpty(password) && TextUtils.isEmpty(phoneNo) && TextUtils.isEmpty(cname) && TextUtils.isEmpty(cPhoneNo)) {
                    Toast.makeText(PatientSignUp.this, "Insert Data", Toast.LENGTH_LONG).show();
                } else if (!validateUsername() | !validateEmail() | !validatePhoneNo() | !validatePassword() | !ConfirmPassword() | !validateCareUsername() | !validateCarePhoneNo()) {
                    return;
                } else {

                    //passing data from this activity to next activity using intent to store in the db
                    Intent i = new Intent(PatientSignUp.this, SignUp2nd.class);
                    i.putExtra("username", username);
                    i.putExtra("email", email);
                    i.putExtra("number", phoneNo);
                    i.putExtra("password", password);       // all values are passed with their key
                    i.putExtra("cUsername", cname);       // all values are passed with their key
                    i.putExtra("cPhoneNo", cPhoneNo);       // all values are passed with their key
                    startActivity(i);
                }
            }
        });

    }//onCreateEnd

    public void backFunction(View view){
        Intent i = new Intent(getApplicationContext(), StartUpScreen.class);
        startActivity(i);
    }


    private Boolean validateUsername() {
        String val = regUsername.getEditText().getText().toString().trim();
        String noWhiteSpace = "\\A\\w{1,10}\\z";
        if (val.isEmpty()) {
            regUsername.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 10) {
            regUsername.setError("Username too Long");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            regUsername.setError("White spaces are not allowed or Special symbol are not allowed");
            return false;
        } else {
            regUsername.setError(null);
            return true;
        }
    }

    private Boolean validateCareUsername() {
        String val = regCareUsername.getEditText().getText().toString().trim();
        String noWhiteSpace = "\\A\\w{1,10}\\z";
        if (val.isEmpty()) {
            regCareUsername.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 10) {
            regCareUsername.setError("Username too Long");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            regCareUsername.setError("White spaces are not allowed or Special symbol are not allowed");
            return false;
        } else {
            regCareUsername.setError(null);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = regEmail.getEditText().getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            regEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            regEmail.setError("Invalid Email");
            return false;
        } else {
            regEmail.setError(null);
            return true;
        }

    }

    private Boolean validatePhoneNo() {
        String val = regPhoneNo.getEditText().getText().toString().trim();
        int count = 0;
        for (int i = 0, len = val.length(); i < len; i++) {
            if (Character.isDigit(val.charAt(i))) {
                count++;
            }
        }
        if (val.isEmpty()) {
            regPhoneNo.setError("Field cannot be empty");
            return false;
        } else if (count < 10) {
            regPhoneNo.setError("Invalid Phone Number");
            return false;
        } else {
            regPhoneNo.setError(null);
            return true;
        }

    }

    private Boolean validatePassword() {
        String val = regPassword.getEditText().getText().toString().trim();
        String passwordVal = "^" +
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white space
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            regPassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            regPassword.setError("Password is too weak");
            return false;
        } else {
            regPassword.setError(null);
            return true;
        }

    }

    private Boolean ConfirmPassword() {
        String val = regPassword.getEditText().getText().toString().trim();
        String val1 = regConfPassword.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            regConfPassword.setError("Field cannot be empty");
            return false;
        } else if (!val.equals(val1)) {
            regConfPassword.setError("Password are not same");
            return false;
        } else {
            regConfPassword.setError(null);
            return true;
        }
    }

    private Boolean validateCarePhoneNo(){
        String val = regCarePhoneNo.getEditText().getText().toString().trim();
        int count = 0;
        for (int i = 0, len = val.length(); i < len; i++) {
            if (Character.isDigit(val.charAt(i))) {
                count++;
            }
        }
        if (val.isEmpty()) {
            regCarePhoneNo.setError("Field cannot be empty");
            return false;
        } else if (count < 10) {
            regCarePhoneNo.setError("Invalid Phone Number");
            return false;
        } else {
            regCarePhoneNo.setError(null);
            return true;
        }
    }
}