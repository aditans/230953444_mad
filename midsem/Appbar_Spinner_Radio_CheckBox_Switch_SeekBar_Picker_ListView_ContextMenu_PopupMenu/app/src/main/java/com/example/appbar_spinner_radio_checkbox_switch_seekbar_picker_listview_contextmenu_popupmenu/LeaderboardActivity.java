package com.example.appbar_spinner_radio_checkbox_switch_seekbar_picker_listview_contextmenu_popupmenu;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_common);

        Toolbar toolbar = findViewById(R.id.toolbar_common);
        toolbar.setTitle("Leaderboard");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView listView = findViewById(R.id.lv_common);
        List<String> leaders = new ArrayList<>();
        leaders.add("1. Alice - 15 Events");
        leaders.add("2. Bob - 12 Events");
        leaders.add("3. Charlie - 10 Events");
        
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, leaders);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
