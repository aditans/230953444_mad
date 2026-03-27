package com.example.appbar_spinner_radio_checkbox_switch_seekbar_picker_listview_contextmenu_popupmenu;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;
import java.util.List;

public class HelpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_common);

        Toolbar toolbar = findViewById(R.id.toolbar_common);
        toolbar.setTitle("Help / FAQ");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView listView = findViewById(R.id.lv_common);
        List<String> faqs = new ArrayList<>();
        faqs.add("How to register? - Fill the form and click Register.");
        faqs.add("Can I cancel? - Yes, long press in the list.");
        faqs.add("What is VIP? - Includes lounge access and priority.");
        
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, faqs);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String answer = faqs.get(position).split(" - ")[1];
            Toast.makeText(this, answer, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
