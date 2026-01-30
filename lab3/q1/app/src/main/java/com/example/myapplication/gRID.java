package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class gRID extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_grid);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ArrayList <String> items = new ArrayList<>();
        items.add("apple");
        items.add("banana");
        items.add("cranberry");
        items.add("dates");
        ArrayAdapter adapter= new ArrayAdapter(this, android.R.layout.simple_list_item_1,items);
        TextInputEditText input = findViewById(R.id.lll);
        GridView gv = findViewById(R.id.grid);
        gv.setAdapter(adapter);
        Button nex = findViewById(R.id.button3);
        Button add = findViewById(R.id.button4);
        add.setOnClickListener(v->{
            String text = input.getText().toString().trim();
            if(!text.isEmpty())
            {
                items.add(text);
                adapter.notifyDataSetChanged();
            }

        });
        nex.setOnClickListener(v->{
            Intent intent = new Intent(gRID.this, TabLayoutactivity.class);
            startActivity(intent);

        });

    }
}