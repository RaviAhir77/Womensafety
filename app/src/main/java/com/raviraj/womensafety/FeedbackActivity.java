package com.raviraj.womensafety;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class FeedbackActivity extends Activity {

    private RatingBar ratingBar;
    private EditText feedbackMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);

        // Find views by their IDs
        ratingBar = findViewById(R.id.ratingBar);
        feedbackMessage = findViewById(R.id.feedbackMessage);
        Button submitFeedbackButton = findViewById(R.id.submitFeedbackButton);

        // Set an onClickListener for the submit button
        submitFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitFeedback();
            }
        });
    }

    private void submitFeedback() {
        // Get the user's selected rating
        float rating = ratingBar.getRating();

        // Get the feedback message
        String message = feedbackMessage.getText().toString();

        // Perform any action with the rating and feedback message (e.g., send to a server)
        // For this example, we'll just display a toast message
        String feedbackText = "Rating: " + rating + "\nFeedback: " + message;
        Toast.makeText(this, feedbackText, Toast.LENGTH_LONG).show();
    }
}

