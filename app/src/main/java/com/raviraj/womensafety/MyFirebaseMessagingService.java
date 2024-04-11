package com.raviraj.womensafety;

import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMessaging";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // Check if the message contains data
        if (remoteMessage.getData().size() > 0) {
            // Handle the data payload
            handleDataMessage(remoteMessage.getData());
        }

        // Check if the message contains notification
        if (remoteMessage.getNotification() != null) {
            // Handle the notification payload
            handleNotification(remoteMessage.getNotification());
        }
    }

    // Handle data payload of the FCM message
    private void handleDataMessage(Map<String, String> data) {
        // Extract data from the message and take appropriate action
        // For example, display a notification or perform a specific task
        String title = data.get("title");
        String body = data.get("body");
        Log.d(TAG, "Data Message Received: " + title + " - " + body);

        // Display a notification
        showNotification(title, body);
    }

    // Handle notification payload of the FCM message
    private void handleNotification(RemoteMessage.Notification notification) {
        // Extract notification data and display it
        String title = notification.getTitle();
        String body = notification.getBody();
        Log.d(TAG, "Notification Received: " + title + " - " + body);

        // Display a notification
        showNotification(title, body);
    }

    private void showNotification(String title, String message) {
        // Create a notification and display it using NotificationManager
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                .setSmallIcon(R.drawable.logo) // replace with your own icon
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        int notificationId = (int) System.currentTimeMillis(); // Use current time as notification ID
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(notificationId, builder.build());
    }



    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d(TAG, "Refreshed token: " + token);

        // Get current user ID
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String userId = firebaseAuth.getCurrentUser().getUid();

        // Store the token in the Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users").child(userId);
        usersRef.child("token").setValue(token)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Token saved"))
                .addOnFailureListener(e -> Log.e(TAG, "Failed to save token: " + e.getMessage()));
    }

}
