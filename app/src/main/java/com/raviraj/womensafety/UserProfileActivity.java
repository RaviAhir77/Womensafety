package com.raviraj.womensafety;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends AppCompatActivity {

    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_info); // Replace with the name of your XML layout file

        // Get references to TextViews
        TextView nameTextView = findViewById(R.id.textViewName);
        TextView emailTextView = findViewById(R.id.textViewEmail);
        //TextView passwordTextView = findViewById(R.id.teViewpass1);

        // Get current user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Retrieve user data
                        String userName = dataSnapshot.child("username").getValue(String.class);
                        String userEmail = dataSnapshot.child("email").getValue(String.class);
                        String userPassword = dataSnapshot.child("password").getValue(String.class);

                        // Set user data to TextViews
                        nameTextView.setText(userName);
                        emailTextView.setText(userEmail);
                        //passwordTextView.setText(userPassword);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle onCancelled
                }
            });
        }

        // Logout Button
        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logout the user
                FirebaseAuth.getInstance().signOut();
                // Redirect to login page
                startActivity(new Intent(UserProfileActivity.this, LoginActivity.class));
                finish(); // Close the UserProfileActivity
            }
        });
    }
}
