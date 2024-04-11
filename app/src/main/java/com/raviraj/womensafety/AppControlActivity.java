package com.raviraj.womensafety;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AppControlActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_on_off);

        // Find the App On and App Off buttons by their IDs
        Button appOnButton = findViewById(R.id.app_on_button);
        Button appOffButton = findViewById(R.id.app_off_button);

        // Set an OnClickListener for the App On button
        appOnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implement App On logic here
            }
        });

        // Set an OnClickListener for the App Off button
        appOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implement App Off logic here
            }
        });
    }
}

