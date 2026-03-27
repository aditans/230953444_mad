package com.example.toolbaroptions_spinner_picker_toggle_toast_radio_seek_check;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerDoctor, spinnerDepartment;
    private Button btnPickDate, btnPickTime, btnBook;
    private TextView tvSelectedDate, tvSelectedTime, tvUrgencyLabel;
    private ToggleButton toggleVisitType;
    private RadioGroup rgConsultation;
    private SeekBar sbUrgency;
    private CheckBox cbLab, cbXray, cbPharmacy;

    private String selectedDate = "";
    private String selectedTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinnerDoctor = findViewById(R.id.spinner_doctor);
        spinnerDepartment = findViewById(R.id.spinner_department);
        btnPickDate = findViewById(R.id.btn_pick_date);
        btnPickTime = findViewById(R.id.btn_pick_time);
        tvSelectedDate = findViewById(R.id.tv_selected_date);
        tvSelectedTime = findViewById(R.id.tv_selected_time);
        tvUrgencyLabel = findViewById(R.id.tv_urgency_label);
        toggleVisitType = findViewById(R.id.toggle_visit_type);
        rgConsultation = findViewById(R.id.rg_consultation);
        sbUrgency = findViewById(R.id.sb_urgency);
        cbLab = findViewById(R.id.cb_lab);
        cbXray = findViewById(R.id.cb_xray);
        cbPharmacy = findViewById(R.id.cb_pharmacy);
        btnBook = findViewById(R.id.btn_book);

        setupDoctorSpinner();
        setupDepartmentSpinner();
        setupPickers();
        setupSeekBar();

        btnBook.setOnClickListener(v -> validateAndBook());
    }

    private void setupDoctorSpinner() {
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(new Doctor("Dr. Sharma", "Cardiology"));
        doctors.add(new Doctor("Dr. Mehta", "Neurology"));
        doctors.add(new Doctor("Dr. Rao", "Orthopedics"));

        DoctorAdapter adapter = new DoctorAdapter(this, doctors);
        spinnerDoctor.setAdapter(adapter);
    }

    private void setupDepartmentSpinner() {
        String[] departments = {"Cardiology", "Neurology", "Orthopedics"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, departments);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartment.setAdapter(adapter);
    }

    private void setupPickers() {
        btnPickDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
                selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                tvSelectedDate.setText(selectedDate);
            }, year, month, day);

            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
        });

        btnPickTime.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute1) -> {
                if (hourOfDay >= 9 && hourOfDay < 17) {
                    selectedTime = String.format("%02d:%02d", hourOfDay, minute1);
                    tvSelectedTime.setText(selectedTime);
                } else {
                    Toast.makeText(this, "Please pick a time between 9:00 AM and 5:00 PM", Toast.LENGTH_SHORT).show();
                }
            }, hour, minute, true);
            timePickerDialog.show();
        });
    }

    private void setupSeekBar() {
        sbUrgency.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvUrgencyLabel.setText("Urgency Level: " + (progress + 1) + " / 5");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void validateAndBook() {
        if (selectedDate.isEmpty()) {
            Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedTime.isEmpty()) {
            Toast.makeText(this, "Please select a time", Toast.LENGTH_SHORT).show();
            return;
        }
        int selectedRgId = rgConsultation.getCheckedRadioButtonId();
        if (selectedRgId == -1) {
            Toast.makeText(this, "Please select a consultation type", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton rb = findViewById(selectedRgId);
        String consultationType = rb.getText().toString();
        String doctorName = ((Doctor) spinnerDoctor.getSelectedItem()).getName();
        String department = spinnerDepartment.getSelectedItem().toString();
        String visitType = toggleVisitType.isChecked() ? "Online" : "In-Person";
        int urgency = sbUrgency.getProgress() + 1;

        StringBuilder services = new StringBuilder();
        if (cbLab.isChecked()) services.append("Lab Tests, ");
        if (cbXray.isChecked()) services.append("X-Ray, ");
        if (cbPharmacy.isChecked()) services.append("Pharmacy, ");
        String additionalServices = services.length() > 0 ? services.substring(0, services.length() - 2) : "None";

        String summary = "Doctor: " + doctorName + "\nDept: " + department + "\nDate: " + selectedDate +
                "\nTime: " + selectedTime + "\nVisit: " + visitType + "\nType: " + consultationType +
                "\nUrgency: " + urgency + "\nServices: " + additionalServices;

        Toast.makeText(this, summary, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.putExtra("doctor", doctorName);
        intent.putExtra("department", department);
        intent.putExtra("date", selectedDate);
        intent.putExtra("time", selectedTime);
        intent.putExtra("visitType", visitType);
        intent.putExtra("consultationType", consultationType);
        intent.putExtra("urgency", urgency);
        intent.putExtra("services", additionalServices);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_help) {
            Toast.makeText(this, "Help clicked", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_about) {
            Toast.makeText(this, "About clicked", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this Hospital App!");
            startActivity(Intent.createChooser(shareIntent, "Share App"));
            return true;
        } else if (id == R.id.action_emergency) {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:108"));
            startActivity(callIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}