package com.example.radiobtn_grp_check_seekbar_toggle_switch_toast;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private RadioGroup rgRating;
    private CheckBox cbSyllabus, cbLab, cbFaculty, cbInfrastructure;
    private SeekBar sbSatisfaction;
    private TextView tvSatisfactionLabel;
    private ToggleButton tbRecommend;
    private SwitchCompat swAnonymous;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Handle Window Insets for camera cutout / status bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rgRating = findViewById(R.id.rg_rating);
        cbSyllabus = findViewById(R.id.cb_syllabus);
        cbLab = findViewById(R.id.cb_lab);
        cbFaculty = findViewById(R.id.cb_faculty);
        cbInfrastructure = findViewById(R.id.cb_infrastructure);
        sbSatisfaction = findViewById(R.id.sb_satisfaction);
        tvSatisfactionLabel = findViewById(R.id.tv_satisfaction_label);
        tbRecommend = findViewById(R.id.tb_recommend);
        swAnonymous = findViewById(R.id.sw_anonymous);
        Button btnSubmit = findViewById(R.id.btn_submit);

        // Live update for SeekBar
        sbSatisfaction.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvSatisfactionLabel.setText("Overall Satisfaction: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        btnSubmit.setOnClickListener(v -> submitFeedback());
    }

    private void submitFeedback() {
        int selectedId = rgRating.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, "Please select a course rating", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedRating = findViewById(selectedId);
        String rating = selectedRating.getText().toString();

        StringBuilder improvements = new StringBuilder();
        if (cbSyllabus.isChecked()) improvements.append("Syllabus, ");
        if (cbLab.isChecked()) improvements.append("Lab Facilities, ");
        if (cbFaculty.isChecked()) improvements.append("Faculty, ");
        if (cbInfrastructure.isChecked()) improvements.append("Infrastructure, ");

        String improvementStr = improvements.length() > 0 
            ? improvements.substring(0, improvements.length() - 2) 
            : "None";

        int satisfaction = sbSatisfaction.getProgress();
        String recommend = tbRecommend.isChecked() ? "Yes" : "No";
        boolean isAnonymous = swAnonymous.isChecked();

        // Explicit Intent to navigate to ResultActivity
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("RATING", rating);
        intent.putExtra("IMPROVEMENTS", improvementStr);
        intent.putExtra("SATISFACTION", satisfaction);
        intent.putExtra("RECOMMEND", recommend);
        intent.putExtra("ANONYMOUS", isAnonymous);

        Toast.makeText(this, "Feedback Submitted Successfully!", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            Toast.makeText(this, "Student Feedback App v1.0", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_reset) {
            resetForm();
            return true;
        } else if (id == R.id.action_exit) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void resetForm() {
        rgRating.clearCheck();
        cbSyllabus.setChecked(false);
        cbLab.setChecked(false);
        cbFaculty.setChecked(false);
        cbInfrastructure.setChecked(false);
        sbSatisfaction.setProgress(5);
        tvSatisfactionLabel.setText("Overall Satisfaction: 5");
        tbRecommend.setChecked(false);
        swAnonymous.setChecked(false);
        Toast.makeText(this, "Form Reset", Toast.LENGTH_SHORT).show();
    }
}
