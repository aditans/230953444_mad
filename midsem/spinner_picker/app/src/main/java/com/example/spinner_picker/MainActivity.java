package com.example.spinner_picker;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner movieSpinner, theatreSpinner;
    private Button btnPickDate, btnPickTime, btnBookNow, btnReset;
    private TextView tvSelectedDate, tvSelectedTime;
    private ToggleButton togglePremium;
    private Toolbar toolbar;

    private int selectedHour = -1;
    private String dateString = "", timeString = "";

    private List<String> moviesList, theatresList;
    private ArrayAdapter<String> movieAdapter, theatreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeWidgets();
        setupToolbar();
        setupSpinners();
        setupPickers();
        setupLogic();
    }

    private void initializeWidgets() {
        toolbar = findViewById(R.id.toolbar);
        movieSpinner = findViewById(R.id.movieSpinner);
        theatreSpinner = findViewById(R.id.theatreSpinner);
        btnPickDate = findViewById(R.id.btnPickDate);
        btnPickTime = findViewById(R.id.btnPickTime);
        btnBookNow = findViewById(R.id.btnBookNow);
        btnReset = findViewById(R.id.btnReset);
        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        tvSelectedTime = findViewById(R.id.tvSelectedTime);
        togglePremium = findViewById(R.id.togglePremium);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Movie Booking");
        }
    }

    // --- Spinner Setup ---
    private void setupSpinners() {
        moviesList = new ArrayList<>(Arrays.asList("Inception", "Interstellar", "The Dark Knight", "Avatar"));
        theatresList = new ArrayList<>(Arrays.asList("PVR Cinemas", "Inox", "Cinepolis", "IMAX"));

        movieAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, moviesList);
        movieAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        movieSpinner.setAdapter(movieAdapter);

        theatreAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, theatresList);
        theatreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        theatreSpinner.setAdapter(theatreAdapter);
    }

    // --- Pickers Logic ---
    private void setupPickers() {
        btnPickDate.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
                dateString = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                tvSelectedDate.setText("Date: " + dateString);
            }, year, month, day);
            dpd.show();
        });

        btnPickTime.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            TimePickerDialog tpd = new TimePickerDialog(this, (view, hourOfDay, minute1) -> {
                selectedHour = hourOfDay;
                timeString = String.format("%02d:%02d", hourOfDay, minute1);
                tvSelectedTime.setText("Time: " + timeString);
                validatePremiumTime();
            }, hour, minute, true);
            tpd.show();
        });
    }

    // --- Business Logic ---
    private void setupLogic() {
        togglePremium.setOnCheckedChangeListener((buttonView, isChecked) -> validatePremiumTime());

        btnReset.setOnClickListener(v -> {
            movieSpinner.setSelection(0);
            theatreSpinner.setSelection(0);
            tvSelectedDate.setText("Date: Not Selected");
            tvSelectedTime.setText("Time: Not Selected");
            togglePremium.setChecked(false);
            dateString = "";
            timeString = "";
            selectedHour = -1;
            btnBookNow.setEnabled(true);
        });

        btnBookNow.setOnClickListener(v -> {
            if (dateString.isEmpty() || timeString.isEmpty()) {
                Toast.makeText(this, "Please select Date and Time", Toast.LENGTH_SHORT).show();
                return;
            }

            String details = "Movie: " + movieSpinner.getSelectedItem().toString() +
                    "\nTheatre: " + theatreSpinner.getSelectedItem().toString() +
                    "\nDate: " + dateString +
                    "\nTime: " + timeString +
                    "\nType: " + (togglePremium.isChecked() ? "Premium" : "Standard") +
                    "\nSeats Available: 45";

            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            intent.putExtra("booking_details", details);
            startActivity(intent);
        });
    }

    private void validatePremiumTime() {
        if (togglePremium.isChecked()) {
            if (selectedHour != -1 && selectedHour < 12) {
                btnBookNow.setEnabled(false);
                Toast.makeText(this, "Premium tickets available only after 12:00 PM", Toast.LENGTH_SHORT).show();
            } else {
                btnBookNow.setEnabled(true);
            }
        } else {
            btnBookNow.setEnabled(true);
        }
    }

    // --- Options Menu ---
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_movie) {
            showAddDialog("Movie");
            return true;
        } else if (id == R.id.add_theatre) {
            showAddDialog("Theatre");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAddDialog(String type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add " + type);

        final EditText input = new EditText(this);
        input.setHint("Enter " + type + " name");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);

        builder.setPositiveButton("Confirm", (dialog, which) -> {
            String newName = input.getText().toString().trim();
            if (!newName.isEmpty()) {
                if (type.equals("Movie")) {
                    moviesList.add(newName);
                    movieAdapter.notifyDataSetChanged();
                } else {
                    theatresList.add(newName);
                    theatreAdapter.notifyDataSetChanged();
                }
                Toast.makeText(MainActivity.this, newName + " added successfully", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}