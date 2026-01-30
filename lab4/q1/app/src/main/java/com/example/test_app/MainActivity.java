package com.example.test_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private void showCustomToast(String versionName, int iconResId) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, null);

        TextView toastText = layout.findViewById(R.id.toastText);
        ImageView toastIcon = layout.findViewById(R.id.toastIcon);

        toastText.setText(versionName);
        toastIcon.setImageResource(iconResId);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

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

        Button btn = findViewById(R.id.button);
        ToggleButton tbtn = findViewById(R.id.toggleButton);

        btn.setOnClickListener(v->{
            Toast.makeText(getApplicationContext(), "Button clicked!", Toast.LENGTH_SHORT).show();

        });

        tbtn.setOnClickListener(v->{
            Toast.makeText(getApplicationContext(), "ToggleButton clicked!", Toast.LENGTH_SHORT).show();
        });
    }
}