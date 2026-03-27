package com.example.appbar_options_menu_list_toast_toggle_radiobtn_grp_spinner_picker;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        textView.setText("Your Cart is Empty");
        textView.setPadding(32, 32, 32, 32);
        setContentView(textView);
    }
}