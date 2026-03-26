package com.example.spinner_vehicle_park;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // Global variables for widgets
    private Spinner spinnerVehicleType;
    private EditText etVehicleNumber, etRcNumber;
    private Button btnSubmit, btnEdit, btnConfirm;
    private LinearLayout layoutRegistration, layoutConfirmation;
    private TextView tvDisplayDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Initialize Widgets
        initWidgets();

        // 2. Spinner Setup Logic
        setupSpinner();

        // 3. Submit Button Logic
        setupSubmitButton();

        // 4. Confirmation Logic (Edit/Confirm)
        setupConfirmationButtons();
    }

    private void initWidgets() {
        spinnerVehicleType = findViewById(R.id.spinner_vehicle_type);
        etVehicleNumber = findViewById(R.id.et_vehicle_number);
        etRcNumber = findViewById(R.id.et_rc_number);
        btnSubmit = findViewById(R.id.btn_submit);
        btnEdit = findViewById(R.id.btn_edit);
        btnConfirm = findViewById(R.id.btn_confirm);
        layoutRegistration = findViewById(R.id.layout_registration);
        layoutConfirmation = findViewById(R.id.layout_confirmation);
        tvDisplayDetails = findViewById(R.id.tv_display_details);
    }

    // --- SPINNER LOGIC ---
    private void setupSpinner() {
        String[] vehicleTypes = {"Car", "Bike", "Truck", "Scooter"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, 
                android.R.layout.simple_spinner_item, vehicleTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVehicleType.setAdapter(adapter);
    }

    // --- SUBMIT BUTTON LOGIC ---
    private void setupSubmitButton() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = spinnerVehicleType.getSelectedItem().toString();
                String vNum = etVehicleNumber.getText().toString().trim();
                String rcNum = etRcNumber.getText().toString().trim();

                // Validation
                if (vNum.isEmpty() || rcNum.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Display summary
                String summary = "Vehicle Type: " + type + 
                                 "\nVehicle No: " + vNum + 
                                 "\nRC Number: " + rcNum;
                tvDisplayDetails.setText(summary);

                // Switch Views
                layoutRegistration.setVisibility(View.GONE);
                layoutConfirmation.setVisibility(View.VISIBLE);
            }
        });
    }

    // --- CONFIRMATION BUTTONS LOGIC ---
    private void setupConfirmationButtons() {
        // Edit Button: Go back to form
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutConfirmation.setVisibility(View.GONE);
                layoutRegistration.setVisibility(View.VISIBLE);
            }
        });

        // Confirm Button: Final Allotment
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Generate a unique serial number
                int serialNo = new Random().nextInt(9000) + 1000;
                String msg = "Parking Allotted! Serial No: PRK-" + serialNo;
                
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
                
                // Optional: Clear form and reset
                etVehicleNumber.setText("");
                etRcNumber.setText("");
                layoutConfirmation.setVisibility(View.GONE);
                layoutRegistration.setVisibility(View.VISIBLE);
            }
        });
    }
}
