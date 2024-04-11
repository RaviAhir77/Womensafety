package com.raviraj.womensafety;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);


        // Find views in the homepage layout
        Button reportButton = findViewById(R.id.report);
        Button policeStationButton = findViewById(R.id.police_station);
        Button familyMemberButton = findViewById(R.id.family_member);
        Button feedbackButton = findViewById(R.id.feedback_field);
        Button activeAppButton = findViewById(R.id.activate_app);
        ImageView profileIcon = findViewById(R.id.profile_icon);

        // Set onClickListeners for buttons
        familyMemberButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, AddFamilyMembersActivity.class));
            }
        });

        activeAppButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, AppControlActivity.class));
            }
        });

        feedbackButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, FeedbackActivity.class));
            }
        });

        policeStationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, PoliceStationActivity.class));
            }
        });

        reportButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, LocationActivity.class));
            }
        });

        // Profile icon click behavior
        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, UserProfileActivity.class));
            }
        });
    }
}
