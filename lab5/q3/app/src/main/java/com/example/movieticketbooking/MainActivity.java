package com.example.movieticketbooking;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerMovie, spinnerTheatre;
    private Button btnPickDate, btnPickTime, btnBookNow, btnReset;
    private TextView tvSelectedDate, tvSelectedTime;
    private ToggleButton toggleTicketType;

    private int year, month, day, hour, minute;
    private boolean isDateSelected = false;
    private boolean isTimeSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views
        spinnerMovie = findViewById(R.id.spinnerMovie);
        spinnerTheatre = findViewById(R.id.spinnerTheatre);
        btnPickDate = findViewById(R.id.btnPickDate);
        btnPickTime = findViewById(R.id.btnPickTime);
        btnBookNow = findViewById(R.id.btnBookNow);
        btnReset = findViewById(R.id.btnReset);
        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        tvSelectedTime = findViewById(R.id.tvSelectedTime);
        toggleTicketType = findViewById(R.id.toggleTicketType);

        // Populate Spinners
        String[] movies = {"Inception", "Interstellar", "The Dark Knight", "Tenet", "Dunkirk"};
        String[] theatres = {"PVR Cinemas", "Inox Movies", "Cinepolis", "Carnival Cinemas"};

        ArrayAdapter<String> movieAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, movies);
        movieAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMovie.setAdapter(movieAdapter);

        ArrayAdapter<String> theatreAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, theatres);
        theatreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTheatre.setAdapter(theatreAdapter);

        // Current Date and Time
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        // Date Picker
        btnPickDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                    (view, year1, monthOfYear, dayOfMonth) -> {
                        year = year1;
                        month = monthOfYear;
                        day = dayOfMonth;
                        tvSelectedDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1);
                        isDateSelected = true;
                    }, year, month, day);
            datePickerDialog.show();
        });

        // Time Picker
        btnPickTime.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this,
                    (view, hourOfDay, minuteOfHour) -> {
                        hour = hourOfDay;
                        minute = minuteOfHour;
                        tvSelectedTime.setText(String.format("%02d:%02d", hourOfDay, minuteOfHour));
                        isTimeSelected = true;
                        updateButtonState();
                    }, hour, minute, true);
            timePickerDialog.show();
        });

        // Toggle Button Listener
        toggleTicketType.setOnCheckedChangeListener((buttonView, isChecked) -> updateButtonState());

        // Book Now Button
        btnBookNow.setOnClickListener(v -> {
            if (!isDateSelected || !isTimeSelected) {
                Toast.makeText(MainActivity.this, "Please select date and time", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            intent.putExtra("movie", spinnerMovie.getSelectedItem().toString());
            intent.putExtra("theatre", spinnerTheatre.getSelectedItem().toString());
            intent.putExtra("date", tvSelectedDate.getText().toString());
            intent.putExtra("time", tvSelectedTime.getText().toString());
            intent.putExtra("type", toggleTicketType.isChecked() ? "Premium" : "Standard");
            startActivity(intent);
        });

        // Reset Button
        btnReset.setOnClickListener(v -> resetFields());
    }

    private void updateButtonState() {
        if (toggleTicketType.isChecked()) {
            // Premium selected
            if (isTimeSelected && hour >= 12) {
                btnBookNow.setEnabled(true);
            } else {
                btnBookNow.setEnabled(false);
                if (isTimeSelected) {
                    Toast.makeText(this, "Premium tickets only available after 12:00 PM", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            // Standard selected
            btnBookNow.setEnabled(true);
        }
    }

    private void resetFields() {
        spinnerMovie.setSelection(0);
        spinnerTheatre.setSelection(0);
        
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        
        tvSelectedDate.setText("No date selected");
        tvSelectedTime.setText("No time selected");
        isDateSelected = false;
        isTimeSelected = false;
        toggleTicketType.setChecked(false);
        btnBookNow.setEnabled(true);
    }
}
