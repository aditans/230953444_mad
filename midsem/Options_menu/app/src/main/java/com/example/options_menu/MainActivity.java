package com.example.options_menu;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    // Global references for widgets
    Toolbar toolbar;
    TextView tvDisplay;
    ImageView ivDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // --- Widget Initialization ---
        toolbar = findViewById(R.id.toolbar);
        tvDisplay = findViewById(R.id.tvDisplay);
        ivDisplay = findViewById(R.id.ivDisplay);

        // --- Toolbar / App Bar Setup ---
        // Necessary to show the options menu when using a manual Toolbar
        setSupportActionBar(toolbar);
    }

    // --- Options Menu Logic ---

    // 1. Inflate the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // 2. Handle Menu Item Clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Reset visibility for each click
        ivDisplay.setVisibility(View.GONE);

        // Workout Plans Logic
        if (id == R.id.menu_workout) {
            tvDisplay.setText("WORKOUT PROGRAMS:\n1. Weight Loss\n2. Cardio\n3. Strength Training");
            return true;
        } 
        // Trainers Logic
        else if (id == R.id.menu_trainers) {
            tvDisplay.setText("OUR TRAINERS:\nMike (Yoga Specialist)\nSarah (HIIT Expert)");
            ivDisplay.setImageResource(android.R.drawable.ic_menu_gallery); 
            ivDisplay.setVisibility(View.VISIBLE);
            return true;
        } 
        // Membership Logic
        else if (id == R.id.menu_membership) {
            tvDisplay.setText("MEMBERSHIP PACKAGES:\nBasic: $20/mo\nPro: $50/mo\nElite: $80/mo");
            return true;
        }
        // Homepage Logic
        else if (id == R.id.menu_home) {
            tvDisplay.setText("Welcome to XYZ Fitness Homepage!");
            return true;
        }
        // About Us Logic
        else if (id == R.id.menu_about) {
            tvDisplay.setText("XYZ Fitness: Best in town since 2010.");
            return true;
        }
        // Contact Us Logic
        else if (id == R.id.menu_contact) {
            tvDisplay.setText("Contact Us: +123 456 789\nEmail: info@xyzfitness.com");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}