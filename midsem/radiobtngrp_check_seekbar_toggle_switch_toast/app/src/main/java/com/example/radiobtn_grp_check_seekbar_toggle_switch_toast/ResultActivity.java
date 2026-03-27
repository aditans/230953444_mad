package com.example.radiobtn_grp_check_seekbar_toggle_switch_toast;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Handle Window Insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar_result);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TextView tvSummary = findViewById(R.id.tv_summary);

        // Get data from Intent
        Intent intent = getIntent();
        String rating = intent.getStringExtra("RATING");
        String improvements = intent.getStringExtra("IMPROVEMENTS");
        int satisfaction = intent.getIntExtra("SATISFACTION", 0);
        String recommend = intent.getStringExtra("RECOMMEND");
        boolean isAnonymous = intent.getBooleanExtra("ANONYMOUS", false);

        // Format summary
        String summary = "Student Feedback Summary\n\n" +
                "Course Rating: " + rating + "\n" +
                "Areas for Improvement: " + improvements + "\n" +
                "Overall Satisfaction: " + satisfaction + "/10\n" +
                "Recommends Course: " + recommend + "\n" +
                "Anonymous Submission: " + (isAnonymous ? "Yes" : "No");

        tvSummary.setText(summary);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
