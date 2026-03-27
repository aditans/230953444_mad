package com.example.appbar_options_menu_list_toast_toggle_radiobtn_grp_spinner_picker;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listView = new ListView(this);
        setContentView(listView);
        String[] dishes = {"Paneer Tikka", "Spring Rolls", "Margherita Pizza", "Tacos", "Pasta", "Noodles"};
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dishes));
    }
}