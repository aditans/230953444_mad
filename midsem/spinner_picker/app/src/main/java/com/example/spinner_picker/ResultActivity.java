package com.example.spinner_picker;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        TextView textView = new TextView(this);
        textView.setPadding(50, 100, 50, 50);
        textView.setTextSize(20);
        
        String details = getIntent().getStringExtra("booking_details");
        if (details != null) {
            textView.setText("BOOKING SUCCESSFUL!\n\n" + details);
        } else {
            textView.setText("No details found.");
        }
        
        setContentView(textView);
    }
}