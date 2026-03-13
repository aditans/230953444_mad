package com.example.grocery;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etItemName, etItemCost;
    private Button btnAddItem, btnAddToCart, btnClear;
    private Spinner spinnerItems;
    private TextView tvTotalCost;
    private DatabaseHelper dbHelper;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "GroceryPrefs";
    private static final String KEY_TOTAL_COST = "total_cost";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etItemName = findViewById(R.id.etItemName);
        etItemCost = findViewById(R.id.etItemCost);
        btnAddItem = findViewById(R.id.btnAddItem);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnClear = findViewById(R.id.btnClear);
        spinnerItems = findViewById(R.id.spinnerItems);
        tvTotalCost = findViewById(R.id.tvTotalCost);

        dbHelper = new DatabaseHelper(this);
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Load total cost from Shared Preferences
        float currentTotal = sharedPreferences.getFloat(KEY_TOTAL_COST, 0.0f);
        tvTotalCost.setText(getString(R.string.total_cost_label, (double) currentTotal));

        refreshSpinner();

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etItemName.getText().toString().trim();
                String costStr = etItemCost.getText().toString().trim();

                if (name.isEmpty() || costStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, R.string.enter_details, Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    double cost = Double.parseDouble(costStr);
                    long id = dbHelper.insertItem(name, cost);

                    if (id != -1) {
                        Toast.makeText(MainActivity.this, R.string.item_added, Toast.LENGTH_SHORT).show();
                        etItemName.setText("");
                        etItemCost.setText("");
                        refreshSpinner();
                    } else {
                        Toast.makeText(MainActivity.this, R.string.error_adding, Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Invalid cost", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinnerItems.getSelectedItem() == null) {
                    Toast.makeText(MainActivity.this, R.string.no_items, Toast.LENGTH_SHORT).show();
                    return;
                }

                String selectedItem = spinnerItems.getSelectedItem().toString();
                double cost = dbHelper.getItemCost(selectedItem);

                float currentTotal = sharedPreferences.getFloat(KEY_TOTAL_COST, 0.0f);
                float newTotal = currentTotal + (float) cost;

                // Save new total to Shared Preferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putFloat(KEY_TOTAL_COST, newTotal);
                editor.apply();

                tvTotalCost.setText(getString(R.string.total_cost_label, (double) newTotal));
                Toast.makeText(MainActivity.this, getString(R.string.added_to_total, selectedItem), Toast.LENGTH_SHORT).show();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putFloat(KEY_TOTAL_COST, 0.0f);
                editor.apply();
                tvTotalCost.setText(getString(R.string.total_cost_label, 0.0));
                Toast.makeText(MainActivity.this, R.string.total_reset, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void refreshSpinner() {
        List<String> items = dbHelper.getAllItemNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerItems.setAdapter(adapter);
    }
}
