package com.example.myapplication;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.activity.EdgeToEdge;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_workout_plans) {
            showWorkoutPlans();
            return true;
        } else if (id == R.id.menu_trainers) {
            showTrainers();
            return true;
        } else if (id == R.id.menu_membership) {
            showMembership();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showWorkoutPlans() {
        String[] plans = {"Weight Loss", "Cardio", "Muscle Building", "Yoga", "HIIT"};
        new AlertDialog.Builder(this)
                .setTitle("Workout Plans")
                .setItems(plans, null)
                .setPositiveButton("Close", null)
                .show();
    }

    private void showTrainers() {
        // Displaying trainers with names and specializations. 
        // Note: For a "simple" implementation, we use text. 
        // In a real app, you would use a custom layout to show actual photo images.
        String trainersInfo = "1. John Doe - Strength Training\n" +
                "2. Jane Smith - Yoga & Pilates\n" +
                "3. Mike Ross - HIIT & Cardio";
        new AlertDialog.Builder(this)
                .setTitle("Our Trainers")
                .setMessage(trainersInfo)
                .setIcon(android.R.drawable.ic_menu_gallery) // Placeholder icon for "photos"
                .setPositiveButton("Close", null)
                .show();
    }

    private void showMembership() {
        String membershipDetails = "Basic: $20/month\n- Gym Access\n\n" +
                "Pro: $50/month\n- Gym + Trainer Support\n\n" +
                "Elite: $80/month\n- All Access + Spa + Sauna";
        new AlertDialog.Builder(this)
                .setTitle("Membership Packages")
                .setMessage(membershipDetails)
                .setPositiveButton("Close", null)
                .show();
    }
}
