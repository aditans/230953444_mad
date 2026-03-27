package com.example.toggle_spinner_picker_pop_up_menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DeploymentDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_deployment_details);

        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        Spinner spinnerZone = findViewById(R.id.spinnerZone);
        Spinner spinnerTask = findViewById(R.id.spinnerTask);
        DatePicker datePicker = findViewById(R.id.datePicker);
        TimePicker timePicker = findViewById(R.id.timePicker);
        Button btnNextDeployment = findViewById(R.id.btnNextDeployment);

        String[] zones = {"North", "South", "East", "West"};
        String[] tasks = {"Medical Aid", "Logistics", "Food Distribution"};

        ArrayAdapter<String> zoneAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, zones);
        zoneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerZone.setAdapter(zoneAdapter);

        ArrayAdapter<String> taskAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tasks);
        taskAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTask.setAdapter(taskAdapter);

        Intent incomingIntent = getIntent();

        btnNextDeployment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String zone = spinnerZone.getSelectedItem().toString();
                String task = spinnerTask.getSelectedItem().toString();
                String date = datePicker.getDayOfMonth() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear();
                String time = timePicker.getHour() + ":" + timePicker.getMinute();

                Intent intent = new Intent(DeploymentDetailsActivity.this, ShiftSelectionActivity.class);
                intent.putExtras(incomingIntent.getExtras());
                intent.putExtra("zone", zone);
                intent.putExtra("task", task);
                intent.putExtra("date", date);
                intent.putExtra("time", time);
                startActivity(intent);
                Toast.makeText(DeploymentDetailsActivity.this, "Deployment details saved", Toast.LENGTH_SHORT).show();
            }
        });
    }
}