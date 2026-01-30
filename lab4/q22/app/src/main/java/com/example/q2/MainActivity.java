package com.example.q2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

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
        Button cupcake = findViewById(R.id.Cupcake);
        Button jellybean = findViewById(R.id.JellyBean);
        Button oreo = findViewById(R.id.Oreo);
        Button lollipop = findViewById(R.id.Lollipop);

        cupcake.setOnClickListener(v->{
            showCustomToast("Android Cupcake", R.drawable.cupcake);
        });
        jellybean.setOnClickListener(v->{
            showCustomToast("Android JellyBean", R.drawable.jelly);
        });
        oreo.setOnClickListener(v->{
            showCustomToast("Android Oreo", R.drawable.oreo);
        });
        lollipop.setOnClickListener(v->{
            showCustomToast("Android Lollipop", R.drawable.img);
        });

    }
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
}