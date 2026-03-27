package com.example.toggle_zoom_toast;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.ZoomButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private ImageView statusIcon;
    private ToggleButton toggleAirplane;
    private ZoomButton zoomIn, zoomOut;
    private Button btnOpenBrowser, btnSendReport;
    private float iconScale = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        statusIcon = findViewById(R.id.statusIcon);
        toggleAirplane = findViewById(R.id.toggleAirplane);
        zoomIn = findViewById(R.id.zoomIn);
        zoomOut = findViewById(R.id.zoomOut);
        btnOpenBrowser = findViewById(R.id.btnOpenBrowser);
        btnSendReport = findViewById(R.id.btnSendReport);

        toggleAirplane.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                statusIcon.setImageResource(R.drawable.ic_airplane_on);
                Toast.makeText(MainActivity.this, "Airplane Mode: ON", Toast.LENGTH_SHORT).show();
            } else {
                statusIcon.setImageResource(R.drawable.ic_airplane_off);
                Toast.makeText(MainActivity.this, "Airplane Mode: OFF", Toast.LENGTH_SHORT).show();
            }
        });

        zoomIn.setOnClickListener(v -> {
            iconScale += 0.1f;
            statusIcon.setScaleX(iconScale);
            statusIcon.setScaleY(iconScale);
            Toast.makeText(MainActivity.this, "Zoomed In", Toast.LENGTH_SHORT).show();
        });

        zoomOut.setOnClickListener(v -> {
            if (iconScale > 0.2f) {
                iconScale -= 0.1f;
                statusIcon.setScaleX(iconScale);
                statusIcon.setScaleY(iconScale);
                Toast.makeText(MainActivity.this, "Zoomed Out", Toast.LENGTH_SHORT).show();
            }
        });

        btnOpenBrowser.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
            startActivity(intent);
            Toast.makeText(MainActivity.this, "Opening Browser...", Toast.LENGTH_SHORT).show();
        });

        btnSendReport.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Connectivity Report");
            intent.putExtra(Intent.EXTRA_TEXT, "Here is my connectivity report.");
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Composing Email...", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "No email app found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_about) {
            Toast.makeText(this, "About clicked", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_exit) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}