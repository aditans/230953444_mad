package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TabLayoutactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tab_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ViewPager2 pager = findViewById(R.id.pager);
        View tabLayoutactivity = findViewById(R.id.tab);

        pager.setAdapter(new ViewPagerAdapter(this));

        new TabLayoutMediator((TabLayout) tabLayoutactivity, pager,
                (tab, position) -> {
                    if (position == 0) tab.setText("Monday");
                    if (position == 1) tab.setText("Tuesday");
                    if (position == 2) tab.setText("Wednesday");
                }).attach();
        Button nex= findViewById(R.id.button5);
        nex.setOnClickListener(v->{
            Intent intent = new Intent(TabLayoutactivity.this,table.class);
            startActivity(intent);

        });

    }
}


