package com.example.sqlbrowser;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;
    private TextView textViewData;
    private TextView textViewPrefValue;
    private EditText editTextPref;
    private Button buttonSavePref;
    private Button buttonRefresh;

    private static final String PREFS_NAME = "MyPrefs";
    private static final String PREF_KEY = "user_input";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        textViewData = findViewById(R.id.textViewData);
        textViewPrefValue = findViewById(R.id.textViewPrefValue);
        editTextPref = findViewById(R.id.editTextPref);
        buttonSavePref = findViewById(R.id.buttonSavePref);
        buttonRefresh = findViewById(R.id.buttonRefresh);

        dbHelper = new MyDatabaseHelper(this);

        // Initial SQLite setup
        insertInitialData();
        displayData();

        // SQLite Refresh Button
        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayData();
                Toast.makeText(MainActivity.this, "Data Refreshed", Toast.LENGTH_SHORT).show();
            }
        });

        // Shared Preferences Logic
        loadPreference();

        buttonSavePref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreference();
            }
        });
    }

    private void insertInitialData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM Users", null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        if (count == 0) {
            ContentValues values1 = new ContentValues();
            values1.put("name", "John Doe");
            values1.put("email", "john.doe@example.com");
            db.insert("Users", null, values1);

            ContentValues values2 = new ContentValues();
            values2.put("name", "Jane Smith");
            values2.put("email", "jane.smith@example.com");
            db.insert("Users", null, values2);
            
            Log.d("DB_INFO", "Initial data inserted.");
        }
    }

    private void displayData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("Users", null, null, null, null, null, null);

        StringBuilder builder = new StringBuilder();
        builder.append("Users in Database:\n\n");

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));

            builder.append("ID: ").append(id)
                    .append(", Name: ").append(name)
                    .append(", Email: ").append(email).append("\n");
        }
        cursor.close();

        textViewData.setText(builder.toString());
    }

    private void savePreference() {
        String input = editTextPref.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_KEY, input);
        editor.apply();
        
        textViewPrefValue.setText("Stored Pref: " + input);
        Toast.makeText(this, "Preference Saved", Toast.LENGTH_SHORT).show();
    }

    private void loadPreference() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String savedValue = sharedPreferences.getString(PREF_KEY, "None");
        textViewPrefValue.setText("Stored Pref: " + savedValue);
    }
}
