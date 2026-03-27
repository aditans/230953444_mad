package com.example.toggle_spinner_picker_pop_up_menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_summary);

        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        TextView tvDetails = findViewById(R.id.tvDetails);
        Button btnModify = findViewById(R.id.btnModify);
        Button btnFinish = findViewById(R.id.btnFinish);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String name = extras.getString("name");
            String email = extras.getString("email");
            String phone = extras.getString("phone");
            boolean availability = extras.getBoolean("availability");
            String zone = extras.getString("zone");
            String task = extras.getString("task");
            String date = extras.getString("date");
            String time = extras.getString("time");
            String shift = extras.getString("shift");

            String summary = "Name: " + name + "\n" +
                    "Email: " + email + "\n" +
                    "Phone: " + phone + "\n" +
                    "Availability: " + (availability ? "Available" : "Not Available") + "\n" +
                    "Zone: " + zone + "\n" +
                    "Task: " + task + "\n" +
                    "Date: " + date + "\n" +
                    "Time: " + time + "\n" +
                    "Shift: " + shift;

            tvDetails.setText(summary);
        }

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SummaryActivity.this, BasicDetailsActivity.class);
                intent.putExtras(getIntent().getExtras());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SummaryActivity.this, "Registration Complete", Toast.LENGTH_SHORT).show();
                finishAffinity();
            }
        });
    }
}