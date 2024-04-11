package com.raviraj.womensafety;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddFamilyMembersActivity extends AppCompatActivity {
    private EditText nameEditText, phoneEditText, emailEditText;
    private Spinner familyMembersSpinner;
    private Button addMemberButton, sendAlertButton, removeMemberButton, editMemberButton;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private ArrayAdapter<FamilyMember> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.family_member);

        // Initialize Firebase components
        firebaseAuth = FirebaseAuth.getInstance();
        String userId = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("family_members");

        // Initialize views
        nameEditText = findViewById(R.id.family_member_1_name);
        phoneEditText = findViewById(R.id.family_member_1_phone);
        emailEditText = findViewById(R.id.family_member_1_email);
        familyMembersSpinner = findViewById(R.id.family_members_spinner);
        addMemberButton = findViewById(R.id.add_member_button);
        sendAlertButton = findViewById(R.id.send_alert_button);
        removeMemberButton = findViewById(R.id.remove_member_button);
        editMemberButton = findViewById(R.id.edit_member_button); // Initialize editMemberButton

        // Populate spinner with family member data from Firebase
        populateSpinner();

        // Handle button click for adding a family member
        addMemberButton.setOnClickListener(view -> {
            // Retrieve data from EditText fields
            String name = nameEditText.getText().toString().trim();
            String phone = phoneEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();

            // Check if any field is empty
            if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                // Display a message indicating that all fields are required
                Toast.makeText(AddFamilyMembersActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create a new FamilyMember object with the retrieved data
            FamilyMember familyMember = new FamilyMember(name, phone, email, userId); // Using userId variable declared earlier

            // Push the family member data to the database
            // Push the family member data to the database
            databaseReference.push().setValue(familyMember)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Data successfully saved
                            Toast.makeText(AddFamilyMembersActivity.this, "Family member added successfully", Toast.LENGTH_SHORT).show();
                            populateSpinner(); // Update spinner after adding new member
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Failed to save data
                            Toast.makeText(AddFamilyMembersActivity.this, "Failed to add family member: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


            // Clear EditText fields
            nameEditText.setText("");
            phoneEditText.setText("");
            emailEditText.setText("");
        });

        // Handle remove button click for each family member
        removeMemberButton.setOnClickListener(view -> {
            FamilyMember selectedMember = (FamilyMember) familyMembersSpinner.getSelectedItem();
            if (selectedMember != null) {
                // Remove the selected family member from Firebase
                DatabaseReference memberRef = databaseReference.child(selectedMember.getKey());
                memberRef.removeValue()
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(AddFamilyMembersActivity.this, "Family member removed", Toast.LENGTH_SHORT).show();
                            populateSpinner(); // Update spinner after removing member
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(AddFamilyMembersActivity.this, "Failed to remove family member: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });

        // Handle edit button click for each family member
        editMemberButton.setOnClickListener(view -> {
            FamilyMember selectedMember = (FamilyMember) familyMembersSpinner.getSelectedItem();
            if (selectedMember != null) {
                // Implement logic to edit the selected family member's details
                // You can use a dialog or start a new activity to edit the details
                // Update the edited details in Firebase
            }
        });

        // Handle button click for sending alerts to selected family members
        sendAlertButton.setOnClickListener(view -> {
            // Call method to send alert to selected family members
            sendAlertToSelectedFamilyMembers(userId);
        });

        // Handle spinner item selection
        familyMembersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                // Retrieve selected family member from the spinner
                FamilyMember selectedMember = (FamilyMember) adapterView.getItemAtPosition(position);

                // Display the selected family member's details
                displayFamilyMemberDetails(selectedMember);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Add your logic for when no item is selected in the spinner
            }
        });
    }

    // Method to populate spinner with family member data from Firebase
    private void populateSpinner() {
        // Listen for changes in the Firebase database and update the spinner accordingly
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<FamilyMember> familyMembers = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FamilyMember familyMember = snapshot.getValue(FamilyMember.class);
                    familyMember.setKey(snapshot.getKey()); // Set the key for each family member
                    familyMembers.add(familyMember);
                }
                spinnerAdapter = new ArrayAdapter<>(AddFamilyMembersActivity.this, android.R.layout.simple_spinner_item, familyMembers);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                familyMembersSpinner.setAdapter(spinnerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });
    }

    // Define the method to display family member details
    private void displayFamilyMemberDetails(FamilyMember familyMember) {
        // Display the details of the selected family member in EditText fields
        nameEditText.setText(familyMember.getName());
        phoneEditText.setText(familyMember.getPhone());
        emailEditText.setText(familyMember.getEmail());
    }

    private void sendAlertToSelectedFamilyMembers(String currentUserUid) {
        List<String> selectedFamilyMemberIds = new ArrayList<>();

        // Retrieve selected family members from spinner adapter
        for (int i = 0; i < spinnerAdapter.getCount(); i++) {
            FamilyMember familyMember = spinnerAdapter.getItem(i);
            if (familyMember != null) {
                selectedFamilyMemberIds.add(familyMember.getUserId());
            }
        }

        // Call FamilyAlertSender method to send alerts
        FamilyAlertSender.sendAlertToFamilyMembers(currentUserUid, selectedFamilyMemberIds);
    }
}

