package com.example.toggle_spinner_picker_pop_up_menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ShiftSelectionActivity extends AppCompatActivity {

    private String selectedShift = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shift_selection);

        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        Button btnSelectShift = findViewById(R.id.btnSelectShift);
        TextView tvSelectedShift = findViewById(R.id.tvSelectedShift);
        Button btnNextShift = findViewById(R.id.btnNextShift);

        btnSelectShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(ShiftSelectionActivity.this, btnSelectShift);
                popupMenu.getMenu().add("Morning");
                popupMenu.getMenu().add("Afternoon");
                popupMenu.getMenu().add("Evening");

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        selectedShift = item.getTitle().toString();
                        tvSelectedShift.setText("Selected Shift: " + selectedShift);
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        btnNextShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedShift.isEmpty()) {
                    Toast.makeText(ShiftSelectionActivity.this, "Please select a shift", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(ShiftSelectionActivity.this, SummaryActivity.class);
                intent.putExtras(getIntent().getExtras());
                intent.putExtra("shift", selectedShift);
                startActivity(intent);
                Toast.makeText(ShiftSelectionActivity.this, "Shift saved", Toast.LENGTH_SHORT).show();
            }
        });
    }
}