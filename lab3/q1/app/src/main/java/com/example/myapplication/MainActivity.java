package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

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

        ListView listview = findViewById(R.id.abc);
        ArrayList<String> items = new ArrayList<>();
        items.add("apple");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,items);
        listview.setAdapter(adapter);

        TextInputEditText def = findViewById(R.id.def);
        Button b = findViewById(R.id.button);
        Button nex = findViewById(R.id.button2);
        b.setOnClickListener(v->{
            String text = def.getText().toString().trim();
            if (!text.isEmpty()){
                items.add(text);
                adapter.notifyDataSetChanged();
                def.setText("");


            }
        });
        nex.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this,gRID.class);
            startActivity(intent);

        });
    }
}