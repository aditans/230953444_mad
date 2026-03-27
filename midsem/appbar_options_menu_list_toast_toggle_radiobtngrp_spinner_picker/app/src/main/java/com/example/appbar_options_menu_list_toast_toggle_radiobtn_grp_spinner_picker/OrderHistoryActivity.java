package com.example.appbar_options_menu_list_toast_toggle_radiobtn_grp_spinner_picker;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity {
    private ListView listView;
    private List<String> pastOrders;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView = new ListView(this);
        setContentView(listView);

        pastOrders = new ArrayList<>(Arrays.asList("Order #101: Pizza, Small", "Order #102: Pasta, Medium", "Order #103: Tacos, Large"));
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pastOrders);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Order Options");
        menu.add(0, v.getId(), 0, "Reorder");
        menu.add(0, v.getId(), 0, "Delete Record");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getTitle().equals("Reorder")) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("reorder", pastOrders.get(info.position));
            startActivity(intent);
            return true;
        } else if (item.getTitle().equals("Delete Record")) {
            pastOrders.remove(info.position);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Record deleted", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onContextItemSelected(item);
    }
}