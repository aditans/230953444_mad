package com.example.mymenu;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView displayImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayImage = findViewById(R.id.display_image);

        // Finding the icon within the layout to trigger the menu
        ImageView menuIcon = findViewById(R.id.menu_icon);

        menuIcon.setOnClickListener(this::showPopupMenu);
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.image_1) {
                displayImage.setImageResource(R.drawable.image1);
                displayImage.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "Image - 1 Selected", Toast.LENGTH_SHORT).show();
                return true;
            } else if (itemId == R.id.image_2) {
                displayImage.setImageResource(R.drawable.image2);
                displayImage.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "Image - 2 Selected", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });

        popupMenu.show();
    }
}
