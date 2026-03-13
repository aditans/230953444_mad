package com.example.moviereview;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText etMovieName, etYear, etPoints;
    Button btnSave, btnView;
    ListView lvMovies;
    MovieDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMovieName = findViewById(R.id.etMovieName);
        etYear = findViewById(R.id.etYear);
        etPoints = findViewById(R.id.etPoints);
        btnSave = findViewById(R.id.btnSave);
        btnView = findViewById(R.id.btnView);
        lvMovies = findViewById(R.id.lvMovies);

        dbHelper = new MovieDatabaseHelper(this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etMovieName.getText().toString();
                String yearStr = etYear.getText().toString();
                String pointsStr = etPoints.getText().toString();

                if (name.isEmpty() || yearStr.isEmpty() || pointsStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                int year = Integer.parseInt(yearStr);
                int points = Integer.parseInt(pointsStr);

                if (points < 1 || points > 5) {
                    Toast.makeText(MainActivity.this, "Points must be between 1 and 5", Toast.LENGTH_SHORT).show();
                    return;
                }

                long id = dbHelper.insertMovie(name, year, points);
                if (id != -1) {
                    Toast.makeText(MainActivity.this, "Review Saved", Toast.LENGTH_SHORT).show();
                    etMovieName.setText("");
                    etYear.setText("");
                    etPoints.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Error saving review", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> movies = dbHelper.getAllMovies();
                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, movies);
                lvMovies.setAdapter(adapter);
            }
        });
    }
}
