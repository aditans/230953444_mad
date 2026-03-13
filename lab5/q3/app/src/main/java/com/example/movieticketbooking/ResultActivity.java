package com.example.movieticketbooking;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView tvMovieDetail = findViewById(R.id.tvMovieDetail);
        TextView tvTheatreDetail = findViewById(R.id.tvTheatreDetail);
        TextView tvDateDetail = findViewById(R.id.tvDateDetail);
        TextView tvTimeDetail = findViewById(R.id.tvTimeDetail);
        TextView tvTypeDetail = findViewById(R.id.tvTypeDetail);
        TextView tvSeatsDetail = findViewById(R.id.tvSeatsDetail);
        Button btnBack = findViewById(R.id.btnBack);

        Intent intent = getIntent();
        if (intent != null) {
            tvMovieDetail.setText("Movie: " + intent.getStringExtra("movie"));
            tvTheatreDetail.setText("Theatre: " + intent.getStringExtra("theatre"));
            tvDateDetail.setText("Date: " + intent.getStringExtra("date"));
            tvTimeDetail.setText("Time: " + intent.getStringExtra("time"));
            tvTypeDetail.setText("Ticket Type: " + intent.getStringExtra("type"));

            // Generate a random number of available seats between 1 and 100
            int availableSeats = new Random().nextInt(100) + 1;
            tvSeatsDetail.setText("Available Seats: " + availableSeats);
        }

        btnBack.setOnClickListener(v -> finish());
    }
}
