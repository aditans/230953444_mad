package com.example.food;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class order_summary extends AppCompatActivity {
    TextView tvOrderDetails, tvTotalCost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_summary);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvOrderDetails = findViewById(R.id.tvOrderDetails);
        tvTotalCost = findViewById(R.id.tvTotalCost);

        String order = getIntent().getStringExtra("order");
        int total = getIntent().getIntExtra("total", 0);

        tvOrderDetails.setText("Ordered Items:\n" + order);
        tvTotalCost.setText("Total Cost: ₹" + total);
    }
}