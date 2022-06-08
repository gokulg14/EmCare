package com.myproj.wear.patient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myproj.wear.R;
import com.myproj.wear.databases.EmNumDb;
import com.myproj.wear.databases.LoginDb;
import com.myproj.wear.helperclasses.EmNumberHelper;

import java.util.ArrayList;
import java.util.List;

public class PickContact extends AppCompatActivity {

    TextView n1, n2, n3, no1, no2, no3;
    ImageView thumbnailIv, thumbnailIv1, thumbnailIv2;
    FloatingActionButton bf1;
    Button toDB;

    private String username;

    RelativeLayout contact1, contact2, contact3;

    public static final int CONTACT_PERMISSION_CODE = 1;
    public static final int CONTACT_PICK_CODE = 2;

    private int count = 0;

    LoginDb loginDb = new LoginDb(this);

    List<String> numbers = new ArrayList<>();
    List<String> names = new ArrayList<>();

    EmNumberHelper numberInfo;
    EmNumDb numDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pick_contact);
        //hooks for name
        n1 = findViewById(R.id.name);
        n2 = findViewById(R.id.name1);
        n3 = findViewById(R.id.name2);
        //hooks for num
        no1 = findViewById(R.id.num);
        no2 = findViewById(R.id.num1);
        no3 = findViewById(R.id.num2);
        //hooks for images
        thumbnailIv = findViewById(R.id.thumbnailTv);
        thumbnailIv1 = findViewById(R.id.thumbnailTv1);
        thumbnailIv2 = findViewById(R.id.thumbnailTv2);
        //hooks for btn
        bf1 = findViewById(R.id.select_contact);
        toDB = findViewById(R.id.contact_confirm);
        //hooks for relative layout
        contact1 = findViewById(R.id.contact_view);
        contact2 = findViewById(R.id.contact_view1);
        contact3 = findViewById(R.id.contact_view2);

        Intent i = getIntent();
        username = i.getStringExtra("username");

        numDb = new EmNumDb(this);  // Constructor is called and database is created

        //adding num from contacts part
        bf1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                if (count < 4) {
                    if (checkConPermission()) {
                        //permission granted, pick contacts
                        pickConIntent();
                    } else {
                        reqConPermission();
                    }
                } else {
                    Toast.makeText(PickContact.this, "Can't pick more than 3 numbers", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void backtoHome(View view) {
        startActivity(new Intent(getApplicationContext(), PatientLogin.class));
        loginDb.updateActiveUser("F",username);
    }

    private boolean checkConPermission() {
        boolean result = ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_CONTACTS) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void reqConPermission() {
        String[] per = {Manifest.permission.READ_CONTACTS};

        ActivityCompat.requestPermissions(this, per, CONTACT_PERMISSION_CODE);
    }

    private void pickConIntent() {
        // intent to pick contacts
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, CONTACT_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //handle permission request results
        if (requestCode == CONTACT_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission granted, can pick contacts
                pickConIntent();
            } else {
                // permission denied
                Toast.makeText(this, "Permission denied...", Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //handle intent results


        if (resultCode == RESULT_OK) {
            // calls when user click a contact from list
            //String numberEm, nameEm;


            if (requestCode == CONTACT_PICK_CODE) {

                Cursor cursor1, cursor2;

                //get data from intent
                Uri uri = data.getData();

                cursor1 = getContentResolver().query(uri, null, null, null, null);

                if (cursor1.moveToFirst()) {
                    // get contact details
                    String contactId = cursor1.getString(cursor1.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                    String contactName = cursor1.getString(cursor1.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                    String contactThumbnail = cursor1.getString(cursor1.getColumnIndexOrThrow(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));
                    String idResults = cursor1.getString(cursor1.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                    int idResultHold = Integer.parseInt(idResults);

                    if (idResultHold == 1) {
                        cursor2 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                        //a contact may have multiple phone numbers

                        while (cursor2.moveToNext()) {

                            //get phone number
                            String contactNumber = cursor2.getString(cursor2.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            //set details

                            if ((TextUtils.isEmpty(n1.getText().toString()))
                                    && (TextUtils.isEmpty(n2.getText().toString()))
                                    && (TextUtils.isEmpty(n3.getText().toString()))) {

                                n1.append(contactName);
                                no1.append(contactNumber);

                                String nameEm = n1.getText().toString();
                                String numberEm = no1.getText().toString();

                                numbers.add(numberEm);
                                names.add(nameEm);
                                //visibility
                                contact1.setVisibility(View.VISIBLE);
                                contact2.setVisibility(View.INVISIBLE);
                                contact3.setVisibility(View.INVISIBLE);
                                //before setting image, check if have or not
                                if (contactThumbnail != null) {
                                    thumbnailIv.setImageURI(Uri.parse(contactThumbnail));
                                } else {
                                    thumbnailIv.setImageResource(R.drawable.ic_baseline_person);

                                }

                            } else if ((!TextUtils.isEmpty(n1.getText().toString()))
                                    && (TextUtils.isEmpty(n2.getText().toString()))
                                    && (TextUtils.isEmpty(n3.getText().toString()))
                                    && (count == 2)) {

                                n2.append(contactName);
                                no2.append(contactNumber);

                                String nameEm = n2.getText().toString();
                                String numberEm = no2.getText().toString();

                                names.add(nameEm);
                                numbers.add(numberEm);

                                //visibility
                                contact1.setVisibility(View.VISIBLE);
                                contact2.setVisibility(View.VISIBLE);
                                contact3.setVisibility(View.INVISIBLE);

                                //before setting image, check if have or not
                                if (contactThumbnail != null) {
                                    thumbnailIv1.setImageURI(Uri.parse(contactThumbnail));
                                } else {
                                    thumbnailIv1.setImageResource(R.drawable.ic_baseline_person);

                                }
                            } else if ((!TextUtils.isEmpty(n1.getText().toString()))
                                    && (!TextUtils.isEmpty(n2.getText().toString()))
                                    && (TextUtils.isEmpty(n3.getText().toString()))
                                    && (count == 3)) {

                                n3.append(contactName);
                                no3.append(contactNumber);

                                String nameEm = n3.getText().toString();
                                String numberEm = no3.getText().toString();

                                names.add(nameEm);
                                numbers.add(numberEm);
                                //visibility
                                contact1.setVisibility(View.VISIBLE);
                                contact2.setVisibility(View.VISIBLE);
                                contact3.setVisibility(View.VISIBLE);
                                toDB.setVisibility(View.VISIBLE);

                                //before setting image, check if have or not
                                if (contactThumbnail != null) {
                                    thumbnailIv2.setImageURI(Uri.parse(contactThumbnail));
                                } else {
                                    thumbnailIv2.setImageResource(R.drawable.ic_baseline_person);

                                }
                            }
                        }
                        cursor2.close();
                    }
                    cursor1.close();
                }
            }
        } else {
            // calls when user click back button
        }
    }

    //sending num to db on the btn click
    public void sendNumToDb(View view){

        boolean isInserted;
        for (int i=0;i<3;i++)
        {
            numberInfo = new EmNumberHelper();
            numberInfo.setName(names.get(i));
            numberInfo.setNumber(numbers.get(i));
            isInserted = numDb.insertNum(numberInfo);
        }
        if (isInserted = true) {
            Toast.makeText(PickContact.this, "Emergency Numbers Inserted", Toast.LENGTH_SHORT).show();
            //showNum();
        } else {
            Toast.makeText(PickContact.this, "Data not Inserted", Toast.LENGTH_SHORT).show();
        }
    }

}