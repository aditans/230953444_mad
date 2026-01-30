package com.example.myapplication;

import android.app.assist.AssistStructure;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btn;
    ToggleButton tbtn ;
    ImageView view ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btn = findViewById(R.id.button);
        tbtn = findViewById(R.id.toggleButton);
        view = findViewById(R.id.imageView);
        updateMode();
        btn.setOnClickListener(v->{
            tbtn.setChecked(!tbtn.isChecked());
            updateMode();
        });
        tbtn.setOnClickListener(v->{

            updateMode();
        });
    }

    private void updateMode() {
        AssistStructure.ViewNode toggleMode;
        if (tbtn.isChecked()) {
            view.setImageResource(R.drawable.wifi);
            Toast.makeText(this, "Current Mode: Wi-Fi", Toast.LENGTH_SHORT).show();
        } else {
            view.setImageResource(R.drawable.img);
            Toast.makeText(this, "Current Mode: Mobile Data", Toast.LENGTH_SHORT).show();
        }
    }
}