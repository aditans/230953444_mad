package com.example.vehicleparkingregistration;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerVehicleType;
    EditText etVehicleNumber, etRCNumber;
    Button btnSubmit, btnConfirm, btnEdit;
    LinearLayout cardDetails;
    TextView tvDetailVehicleType, tvDetailVehicleNumber, tvDetailRCNumber;

    String selectedVehicleType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        spinnerVehicleType    = findViewById(R.id.spinnerVehicleType);
        etVehicleNumber       = findViewById(R.id.etVehicleNumber);
        etRCNumber            = findViewById(R.id.etRCNumber);
        btnSubmit             = findViewById(R.id.btnSubmit);
        btnConfirm            = findViewById(R.id.btnConfirm);
        btnEdit               = findViewById(R.id.btnEdit);
        cardDetails           = findViewById(R.id.cardDetails);
        tvDetailVehicleType   = findViewById(R.id.tvDetailVehicleType);
        tvDetailVehicleNumber = findViewById(R.id.tvDetailVehicleNumber);
        tvDetailRCNumber      = findViewById(R.id.tvDetailRCNumber);

        setupSpinner();
        setupButtons();
    }

    private void setupSpinner() {
        String[] vehicleTypes = {"Select Vehicle Type", "Car", "Bike", "Truck", "Auto Rickshaw", "Bus", "SUV"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                vehicleTypes
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVehicleType.setAdapter(adapter);

        spinnerVehicleType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    selectedVehicleType = "";
                } else {
                    selectedVehicleType = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedVehicleType = "";
            }
        });
    }

    private void setupButtons() {

        // Submit button — validate and show details card
        btnSubmit.setOnClickListener(v -> {
            String vehicleNumber = etVehicleNumber.getText().toString().trim();
            String rcNumber      = etRCNumber.getText().toString().trim();

            if (selectedVehicleType.isEmpty()) {
                Toast.makeText(this, "Please select a vehicle type.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (vehicleNumber.isEmpty()) {
                etVehicleNumber.setError("Enter vehicle number");
                etVehicleNumber.requestFocus();
                return;
            }
            if (rcNumber.isEmpty()) {
                etRCNumber.setError("Enter RC number");
                etRCNumber.requestFocus();
                return;
            }

            // Populate detail card
            tvDetailVehicleType.setText(selectedVehicleType);
            tvDetailVehicleNumber.setText(vehicleNumber);
            tvDetailRCNumber.setText(rcNumber);

            // Show details card, hide submit button
            cardDetails.setVisibility(View.VISIBLE);
            btnSubmit.setVisibility(View.GONE);

            // Disable input fields while reviewing
            setInputsEnabled(false);
        });

        // Edit button — go back to editing
        btnEdit.setOnClickListener(v -> {
            cardDetails.setVisibility(View.GONE);
            btnSubmit.setVisibility(View.VISIBLE);
            setInputsEnabled(true);
        });

        // Confirm button — generate serial number and show toast
        btnConfirm.setOnClickListener(v -> {
            String serialNumber = generateSerialNumber();
            Toast.makeText(
                    this,
                    "✅ Parking Confirmed!\nSerial No: " + serialNumber,
                    Toast.LENGTH_LONG
            ).show();
            resetForm();
        });
    }

    private void setInputsEnabled(boolean enabled) {
        etVehicleNumber.setEnabled(enabled);
        etRCNumber.setEnabled(enabled);
        spinnerVehicleType.setEnabled(enabled);
    }

    private String generateSerialNumber() {
        int number = new Random().nextInt(90000) + 10000; // 5-digit random number
        return "PRK-" + number;
    }

    private void resetForm() {
        etVehicleNumber.setText("");
        etRCNumber.setText("");
        spinnerVehicleType.setSelection(0);
        selectedVehicleType = "";
        cardDetails.setVisibility(View.GONE);
        btnSubmit.setVisibility(View.VISIBLE);
        setInputsEnabled(true);
    }
}