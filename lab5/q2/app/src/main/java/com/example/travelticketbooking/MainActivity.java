package com.example.travelticketbooking;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerSource, spinnerDestination;
    private Button btnPickDate, btnSubmit, btnReset;
    private TextView tvSelectedDate;
    private ToggleButton toggleTripType;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerSource = findViewById(R.id.spinnerSource);
        spinnerDestination = findViewById(R.id.spinnerDestination);
        btnPickDate = findViewById(R.id.btnPickDate);
        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        toggleTripType = findViewById(R.id.toggleTripType);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnReset = findViewById(R.id.btnReset);

        String[] cities = {"New York", "London", "Paris", "Tokyo", "Dubai", "Mumbai", "Bangalore", "Delhi"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSource.setAdapter(adapter);
        spinnerDestination.setAdapter(adapter);

        // Set current date initially
        setCurrentDate();

        btnPickDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year, monthOfYear, dayOfMonth) -> {
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                        updateDateDisplay();
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        btnSubmit.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
            intent.putExtra("source", spinnerSource.getSelectedItem().toString());
            intent.putExtra("destination", spinnerDestination.getSelectedItem().toString());
            intent.putExtra("date", tvSelectedDate.getText().toString());
            intent.putExtra("tripType", toggleTripType.isChecked() ? "Round-Trip" : "One-Way");
            startActivity(intent);
        });

        btnReset.setOnClickListener(v -> {
            spinnerSource.setSelection(0);
            spinnerDestination.setSelection(0);
            toggleTripType.setChecked(false);
            setCurrentDate();
        });
    }

    private void setCurrentDate() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        updateDateDisplay();
    }

    private void updateDateDisplay() {
        tvSelectedDate.setText(mDay + "/" + (mMonth + 1) + "/" + mYear);
    }
}