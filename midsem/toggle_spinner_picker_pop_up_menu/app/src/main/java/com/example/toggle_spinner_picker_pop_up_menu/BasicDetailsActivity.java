package com.example.toggle_spinner_picker_pop_up_menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BasicDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_basic_details);

        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        EditText etName = findViewById(R.id.etName);
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPhone = findViewById(R.id.etPhone);
        ToggleButton toggleAvailability = findViewById(R.id.toggleAvailability);
        Button btnNextBasic = findViewById(R.id.btnNextBasic);

        // Pre-fill if coming back from Summary (Modify)
        Intent incomingIntent = getIntent();
        if (incomingIntent.hasExtra("name")) {
            etName.setText(incomingIntent.getStringExtra("name"));
            etEmail.setText(incomingIntent.getStringExtra("email"));
            etPhone.setText(incomingIntent.getStringExtra("phone"));
            toggleAvailability.setChecked(incomingIntent.getBooleanExtra("availability", false));
        }

        btnNextBasic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String phone = etPhone.getText().toString();
                boolean availability = toggleAvailability.isChecked();

                if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(BasicDetailsActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(BasicDetailsActivity.this, DeploymentDetailsActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                intent.putExtra("phone", phone);
                intent.putExtra("availability", availability);
                startActivity(intent);
                Toast.makeText(BasicDetailsActivity.this, "Basic details saved", Toast.LENGTH_SHORT).show();
            }
        });
    }
}