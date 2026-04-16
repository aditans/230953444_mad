package com.example.techfestpro;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        TextView tvMessage = findViewById(R.id.tvConfirmationMessage);
        Button btnBack = findViewById(R.id.btnBackToHome);

        String name = getIntent().getStringExtra("name");
        tvMessage.setText("Thank you, " + name + "!\nYour registration for TechFest Pro has been received successfully.");

        btnBack.setOnClickListener(v -> finish());
    }
}