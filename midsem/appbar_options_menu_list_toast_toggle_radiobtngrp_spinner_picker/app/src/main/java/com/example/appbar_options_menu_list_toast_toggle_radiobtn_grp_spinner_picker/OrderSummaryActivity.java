package com.example.appbar_options_menu_list_toast_toggle_radiobtn_grp_spinner_picker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class OrderSummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        Toolbar toolbar = findViewById(R.id.toolbarSummary);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Order Summary");
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        TextView tvSummary = findViewById(R.id.tvSummaryDetails);
        Button btnShare = findViewById(R.id.btnShareOrder);

        ArrayList<OrderItem> items = (ArrayList<OrderItem>) getIntent().getSerializableExtra("items");
        String type = getIntent().getStringExtra("type");
        String scheduled = getIntent().getStringExtra("scheduled");

        StringBuilder sb = new StringBuilder();
        sb.append("Order Type: ").append(type).append("\n");
        if (scheduled != null && !scheduled.isEmpty()) {
            sb.append("Scheduled: ").append(scheduled).append("\n");
        }
        sb.append("\nItems:\n");
        if (items != null) {
            for (OrderItem item : items) {
                sb.append("- ").append(item.toString()).append("\n");
            }
        }

        String summaryText = sb.toString();
        tvSummary.setText(summaryText);

        btnShare.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, summaryText);
            startActivity(Intent.createChooser(shareIntent, "Share Order via"));
        });
    }
}