package com.raviraj.womensafety;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class PoliceStationActivity extends Activity {
    private TextView textViewAddress;
    private TextView textViewPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.police_station); // Use your layout file here

        // Initialize UI elements
        Spinner spinnerPoliceStations = findViewById(R.id.spinnerPoliceStations);
        textViewAddress = findViewById(R.id.addressTextView);
        textViewPhoneNumber = findViewById(R.id.phoneNumberTextView);

        // Create an array of police station names for the spinner
        String[] policeStationNames = {"Police Station 1", "Police Station 2", "Police Station 3"};

        // Create an ArrayAdapter for the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, policeStationNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the ArrayAdapter on the spinner
        spinnerPoliceStations.setAdapter(adapter);

        // Set an item selected listener for the spinner
        spinnerPoliceStations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle selection change, for example, display the address and phone number of the selected police station
                String selectedStation = policeStationNames[position];
                String address = "Address for " + selectedStation;
                String phoneNumber = "Phone number for " + selectedStation;

                // Update the TextViews with the selected police station's details
                textViewAddress.setText(address);
                textViewPhoneNumber.setText(phoneNumber);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle no selection (if needed)
            }
        });
    }
}
