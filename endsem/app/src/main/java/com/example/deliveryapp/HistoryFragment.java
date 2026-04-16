package com.example.deliveryapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HistoryFragment extends Fragment {
    private ListView listView;
    private DatabaseHelper dbHelper;
    private SimpleCursorAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        listView = view.findViewById(R.id.historyListView);
        dbHelper = new DatabaseHelper(getContext());

        refreshList();

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            Cursor cursor = (Cursor) adapter.getItem(position);
            String items = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ITEMS));
            String zone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DELIVERY_ZONE));
            String type = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DELIVERY_TYPE));
            String payment = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PAYMENT_METHOD));
            String time = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TIME));
            int cutlery = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CUTLERY));

            String details = "Items: " + items + "\n" +
                             "Zone: " + zone + "\n" +
                             "Type: " + type + "\n" +
                             "Payment: " + payment + "\n" +
                             "Time: " + time + "\n" +
                             "Cutlery: " + (cutlery == 1 ? "Yes" : "No");

            new AlertDialog.Builder(getContext())
                    .setTitle("Order Details")
                    .setMessage(details)
                    .setPositiveButton("OK", null)
                    .show();
        });

        registerForContextMenu(listView);

        return view;
    }

    private void refreshList() {
        Cursor cursor = dbHelper.getAllOrders();
        adapter = new SimpleCursorAdapter(getContext(), android.R.layout.simple_list_item_2, cursor,
                new String[]{DatabaseHelper.COLUMN_ITEMS, DatabaseHelper.COLUMN_STATUS},
                new int[]{android.R.id.text1, android.R.id.text2}, 0);
        listView.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Order Options");
        menu.add(0, 301, 0, "Update Order");
        menu.add(0, 302, 0, "Delete Order");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == 301) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            if (info != null) {
                Cursor cursor = (Cursor) adapter.getItem(info.position);
                int orderId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
                Intent intent = new Intent(getContext(), UpdateOrderActivity.class);
                intent.putExtra("order_id", orderId);
                startActivity(intent);
            }
            return true;
        } else if (item.getItemId() == 302) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            if (info != null) {
                Cursor cursor = (Cursor) adapter.getItem(info.position);
                int orderId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
                dbHelper.deleteOrder(orderId);
                refreshList();
                Toast.makeText(getContext(), "Order Deleted", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshList();
    }
}
