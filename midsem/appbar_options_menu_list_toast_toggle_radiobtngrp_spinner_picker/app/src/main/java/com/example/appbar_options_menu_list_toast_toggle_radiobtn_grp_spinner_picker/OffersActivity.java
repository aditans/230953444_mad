package com.example.appbar_options_menu_list_toast_toggle_radiobtn_grp_spinner_picker;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class OffersActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listView = new ListView(this);
        setContentView(listView);
        String[] coupons = {"SAVE10", "WELCOME20", "FOODIE50", "FREESHIP"};
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, coupons));
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Toast.makeText(this, "Coupon applied: " + coupons[position], Toast.LENGTH_SHORT).show();
        });
    }
}