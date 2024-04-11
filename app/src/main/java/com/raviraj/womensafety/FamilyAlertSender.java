package com.raviraj.womensafety;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FamilyAlertSender {
    private static final String TAG = "FamilyAlertSender";

    public static void sendAlertToFamilyMembers(String currentUserUid, List<String> selectedFamilyMembers) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference familyMembersRef = database.getReference("users").child(currentUserUid).child("family_members");

        familyMembersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String familyMemberUid = snapshot.getKey();
                    if (selectedFamilyMembers.contains(familyMemberUid)) {
                        // Assuming the token is stored as a field named "token" in the database
                        String familyMemberToken = snapshot.child("token").getValue(String.class);

                        // Send FCM message to familyMemberToken
                        sendFCMMessage(familyMemberToken, "Emergency Alert!"); // Pass the message
                        Log.d(TAG, "Sending alert to family member: " + familyMemberUid);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Error retrieving family members: " + databaseError.getMessage());
            }
        });
    }

    private static void sendFCMMessage(String receiverToken, String message) {
        // Prepare the FCM message
        RemoteMessage.Builder builder = new RemoteMessage.Builder(receiverToken);
        builder.setMessageId(Integer.toString(0))
                .setData(createNotificationData(message)) // Set custom data for the notification
                .setTtl(3600); // Time to live in seconds

        RemoteMessage remoteMessage = builder.build();

        // Send the FCM message
        FirebaseMessaging.getInstance().send(remoteMessage);
        Log.d(TAG, "Message sent");
    }

    private static Map<String, String> createNotificationData(String message) {
        Map<String, String> notificationData = new HashMap<>();
        notificationData.put("message", message);
        // Add any other necessary data
        return notificationData;
    }

}
