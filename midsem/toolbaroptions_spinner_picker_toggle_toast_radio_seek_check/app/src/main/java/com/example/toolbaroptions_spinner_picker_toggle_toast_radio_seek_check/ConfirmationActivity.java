package com.example.toolbaroptions_spinner_picker_toggle_toast_radio_seek_check;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        Toolbar toolbar = findViewById(R.id.toolbar_confirmation);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TextView tvSummary = findViewById(R.id.tv_summary);
        Button btnBookAnother = findViewById(R.id.btn_book_another);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String summary = "Doctor: " + extras.getString("doctor") +
                    "\nDepartment: " + extras.getString("department") +
                    "\nDate: " + extras.getString("date") +
                    "\nTime: " + extras.getString("time") +
                    "\nVisit Type: " + extras.getString("visitType") +
                    "\nConsultation: " + extras.getString("consultationType") +
                    "\nUrgency Level: " + extras.getInt("urgency") + " / 5" +
                    "\nServices: " + extras.getString("services");
            tvSummary.setText(summary);
        }

        btnBookAnother.setOnClickListener(v -> finish());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}