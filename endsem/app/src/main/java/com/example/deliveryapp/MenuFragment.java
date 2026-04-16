package com.example.deliveryapp;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.Collections;

public class MenuFragment extends Fragment {
    private ListView listView;
    private MenuAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        listView = view.findViewById(R.id.menuListView);
        Button btnFilter = view.findViewById(R.id.btnFilter);

        adapter = new MenuAdapter(getContext(), MainActivity.foodList);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);

        btnFilter.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(getContext(), v);
            popup.getMenu().add("Sort by Price");
            popup.getMenu().add("Sort by Rating");
            popup.setOnMenuItemClickListener(item -> {
                if (item.getTitle().equals("Sort by Price")) {
                    Collections.sort(MainActivity.foodList, (f1, f2) -> Double.compare(f1.getPrice(), f2.getPrice()));
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Sorted by Rating", Toast.LENGTH_SHORT).show();
                }
                return true;
            });
            popup.show();
        });

        return view;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select Action");
        menu.add(0, 201, 0, "View Ingredients");
        menu.add(0, 202, 0, "Add to Favorites");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == 201) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            Toast.makeText(getContext(), "Ingredients for " + MainActivity.foodList.get(info.position).getName(), Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == 202) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            Toast.makeText(getContext(), MainActivity.foodList.get(info.position).getName() + " added to favorites", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onContextItemSelected(item);
    }
}
