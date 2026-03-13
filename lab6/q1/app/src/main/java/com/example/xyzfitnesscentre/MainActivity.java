package com.example.xyzfitnesscentre;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private TextView contentDisplay;
    private ImageView contentImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        contentDisplay = findViewById(R.id.content_display);
        contentImage = findViewById(R.id.content_image);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Hide image by default, show only for Trainers
        contentImage.setVisibility(View.GONE);

        if (id == R.id.action_workout_plans) {
            contentDisplay.setText(getString(R.string.workout_plans_content));
            return true;
        } else if (id == R.id.action_trainers) {
            contentDisplay.setText(getString(R.string.trainers_content));
            
            // Show a placeholder image for trainers
            contentImage.setImageResource(android.R.drawable.ic_menu_myplaces);
            contentImage.setVisibility(View.VISIBLE);
            return true;
        } else if (id == R.id.action_membership) {
            contentDisplay.setText(getString(R.string.membership_content));
            return true;
        } else if (id == R.id.action_homepage) {
            contentDisplay.setText(getString(R.string.welcome_message));
            return true;
        } else if (id == R.id.action_about_us) {
            contentDisplay.setText(getString(R.string.about_us_content));
            return true;
        } else if (id == R.id.action_contact_us) {
            contentDisplay.setText(getString(R.string.contact_us_content));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
