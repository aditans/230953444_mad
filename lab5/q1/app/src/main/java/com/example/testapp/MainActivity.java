package com.example.testapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private LinearLayout registrationLayout, confirmationLayout;
    private Spinner vehicleTypeSpinner;
    private EditText vehicleNumberEdit, rcNumberEdit;
    private TextView displayDetails;
    private Button submitButton, editButton, confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        registrationLayout = findViewById(R.id.registration_layout);
        confirmationLayout = findViewById(R.id.confirmation_layout);
        vehicleTypeSpinner = findViewById(R.id.vehicle_type_spinner);
        vehicleNumberEdit = findViewById(R.id.vehicle_number_edit);
        rcNumberEdit = findViewById(R.id.rc_number_edit);
        displayDetails = findViewById(R.id.display_details);
        submitButton = findViewById(R.id.submit_button);
        editButton = findViewById(R.id.edit_button);
        confirmButton = findViewById(R.id.confirm_button);

        // Populate Spinner
        String[] vehicleTypes = {"Car", "Bike", "Truck", "Bus", "Scooter"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, vehicleTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicleTypeSpinner.setAdapter(adapter);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = vehicleTypeSpinner.getSelectedItem().toString();
                String vNumber = vehicleNumberEdit.getText().toString().trim();
                String rcNumber = rcNumberEdit.getText().toString().trim();

                if (vNumber.isEmpty() || rcNumber.isEmpty()) {
                    Toast.makeText(MainActivity.this, R.string.msg_fill_all, Toast.LENGTH_SHORT).show();
                    return;
                }

                String details = getString(R.string.select_vehicle_type) + " " + type + "\n" +
                                 getString(R.string.hint_vehicle_number) + ": " + vNumber + "\n" +
                                 getString(R.string.hint_rc_number) + ": " + rcNumber;

                displayDetails.setText(details);
                registrationLayout.setVisibility(View.GONE);
                confirmationLayout.setVisibility(View.VISIBLE);
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmationLayout.setVisibility(View.GONE);
                registrationLayout.setVisibility(View.VISIBLE);
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serialNumber = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
                String message = getString(R.string.msg_parking_allotted, serialNumber);
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                
                // Reset for next registration
                vehicleNumberEdit.setText("");
                rcNumberEdit.setText("");
                confirmationLayout.setVisibility(View.GONE);
                registrationLayout.setVisibility(View.VISIBLE);
            }
        });
    }
}