package com.example.travelticketbooking;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        TextView tvSource = findViewById(R.id.tvDisplaySource);
        TextView tvDestination = findViewById(R.id.tvDisplayDestination);
        TextView tvDate = findViewById(R.id.tvDisplayDate);
        TextView tvTripType = findViewById(R.id.tvDisplayTripType);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tvSource.setText("Source: " + extras.getString("source"));
            tvDestination.setText("Destination: " + extras.getString("destination"));
            tvDate.setText("Date: " + extras.getString("date"));
            tvTripType.setText("Trip Type: " + extras.getString("tripType"));
        }
    }
}