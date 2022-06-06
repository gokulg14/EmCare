package com.myproj.wear.patient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.myproj.wear.R;
import com.myproj.wear.common.LoginSignup.StartUpScreen;
import com.myproj.wear.databases.HealthDataDb;
import com.myproj.wear.databases.LoginDb;
import com.myproj.wear.helperclasses.HealthDataHelper;
import com.myproj.wear.helperclasses.homeAdapter.FeaturedAdapter;
import com.myproj.wear.helperclasses.homeAdapter.FeaturedHelperClass;

import java.util.ArrayList;

public class HomePagePatient extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    // variables
    RecyclerView featuredRecycler;
    RecyclerView.Adapter adapter;
    ImageView menu_icon;
    static final float END_SCALE = 0.7f;
    LinearLayout contentView;

    private String username;

    TextView heartRate;
    TextView bloodPressure;
    LoginDb loginDb = new LoginDb(this);
    HealthDataDb healthDataDb = new HealthDataDb(this);
    //Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home_page_patient);

        // asking permission for sms access
        ActivityCompat.requestPermissions(HomePagePatient.this, new String[]{Manifest.permission.SEND_SMS,
                Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);

        //hooks
        featuredRecycler = findViewById(R.id.featured_recycler);
        //for menu

        heartRate = findViewById(R.id.heart_rate);
        bloodPressure = findViewById(R.id.blood_pressure);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        menu_icon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.menu_content);

        Intent i = getIntent();
        username = i.getStringExtra("patientName");
        HealthDataHelper healthdata = healthDataDb.getLastUpdatedHealthData(username);
        Log.d("HEALTHDATA FOR PATIENT","healthdata"+healthdata);
        heartRate.setText(healthdata.getHeartRateReading());
        bloodPressure.setText(healthdata.getBpReading());

        navigationDrawer();         // popping up nav bar on clicking menu btn

        featuredRecycler();         // for card view in home
    }

    // nav drawer fn
    private void navigationDrawer() {

        //Navigation Drawer
        navigationView.bringToFront();          // interact with nav dr
        navigationView.setNavigationItemSelectedListener(this);         // for selecting
        navigationView.setCheckedItem(R.id.nav_home);

        menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))        //drawer is visible close it
                    drawerLayout.closeDrawer(GravityCompat.START);
                else
                    drawerLayout.openDrawer(GravityCompat.START);           //else open it
            }
        });

        animateNavigationDrawer();              // for animate nav drawer
    }

    private void animateNavigationDrawer() {

        drawerLayout.setScrimColor(getResources().getColor(R.color.light_white));           // clr at right side on selecting nav drawer
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // scale the view based on current slide offset
                final float diffScaledOffset = slideOffset * (1- END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                //translate the view, according for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });
    }

    @Override
    public void onBackPressed() {                           // closing the nav drawer on first back btn and second one for closing the app

        if (drawerLayout.isDrawerVisible(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.nav_contacts:
                Intent i = new Intent(getApplicationContext(),PickContact.class);
                startActivity(i);
                break;
            case R.id.nav_profile:
                Intent i1 = new Intent(getApplicationContext(),UserProfile.class);
                startActivity(i1);
                break;
            case R.id.nav_logout:
                Intent i2 = new Intent(getApplicationContext(),PatientLogin.class);
                startActivity(i2);
                loginDb.updateActiveUser("F",username);
                break;
        }

        return true;
    }

    // recycler and card fn
    private void featuredRecycler() {

        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        ArrayList<FeaturedHelperClass> features = new ArrayList<>();

        features.add(new FeaturedHelperClass(R.drawable.smartwatch_card_img,"Smartwatch Pairing","Pair with smart watch and get your health data"));
        features.add(new FeaturedHelperClass(R.drawable.card_emergency,"Instead Medical Support","Call/SMS ambulance on emergency situations with your location"));
        features.add(new FeaturedHelperClass(R.drawable.card_monitor,"Caretaker can monitor your health","Caretaker can monitor your health from anywhere"));

        adapter = new FeaturedAdapter(features);            //passing arraylist
        featuredRecycler.setAdapter(adapter);
    }
}