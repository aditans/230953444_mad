package com.example.appbar_spinner_radio_checkbox_switch_seekbar_picker_listview_contextmenu_popupmenu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class RegistrationSummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_summary);

        Toolbar toolbar = findViewById(R.id.toolbar_summary);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TextView tvSummary = findViewById(R.id.tv_summary_details);
        Button btnShare = findViewById(R.id.btn_share_summary);

        Registration reg = (Registration) getIntent().getSerializableExtra("registration");

        if (reg != null) {
            String summary = "Event Category: " + reg.getCategory() +
                    "\nTicket Type: " + reg.getTicketType() +
                    "\nAttendance Slot: " + reg.getSlot() +
                    "\nGuests: " + reg.getGuestCount() +
                    "\nAdd-ons: " + reg.getAddOns();
            tvSummary.setText(summary);

            btnShare.setOnClickListener(v -> {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Full Registration Summary:\n" + summary);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            });
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
