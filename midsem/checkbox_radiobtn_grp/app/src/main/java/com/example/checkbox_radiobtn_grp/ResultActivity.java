package com.example.checkbox_radiobtn_grp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private TextView tvOrderDetails;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // 1. Initialize Widgets
        tvOrderDetails = findViewById(R.id.tvOrderDetails);
        btnBack = findViewById(R.id.btnBack);

        // 2. Receive Data via Intent
        String details = getIntent().getStringExtra("orderDetails");
        if (details != null) {
            tvOrderDetails.setText(details);
        }

        // 3. Back Button Logic
        btnBack.setOnClickListener(v -> finish());
    }
}
