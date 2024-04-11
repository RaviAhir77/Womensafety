package com.raviraj.womensafety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.AuthResult;

import java.util.Objects;

public class RegistrationActivity extends AppCompatActivity {

    private EditText usernameEditText, phoneNumberEditText, passwordEditText, emailEditText;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ragister);

        emailEditText = findViewById(R.id.registration_email);
        usernameEditText = findViewById(R.id.registration_username);
        phoneNumberEditText = findViewById(R.id.phone);
        passwordEditText = findViewById(R.id.registration_password);
        Button registerButton = findViewById(R.id.registration_button);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users"); // Change "users" to your desired database node
        TextView loginLink = findViewById(R.id.login_link);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    private boolean validateRegistration() {
        String email = emailEditText.getText().toString().trim();
        String username = usernameEditText.getText().toString().trim();
        String phoneNumber = phoneNumberEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || username.isEmpty() || phoneNumber.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }



    private void registerUser() {

        if (!validateRegistration()) {
            return; // Exit method if registration is not valid
        }


        final String email = emailEditText.getText().toString().trim();
        final String username = usernameEditText.getText().toString().trim();
        final String phoneNumber = phoneNumberEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            assert user != null;
                            String userId = user.getUid();

                            // Create a user object with the provided attributes
                            User newUser = new User(username, phoneNumber, email, userId);

                            // Store the user data in the database
                            databaseReference.child(userId).setValue(newUser);

                            Toast.makeText(RegistrationActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish(); // Close the RegistrationActivity
                        } else {
                            Toast.makeText(RegistrationActivity.this, "Registration failed: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
