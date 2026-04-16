package com.example.techfestpro;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventsFragment extends Fragment {

    private ListView listView;
    private List<String> events = new ArrayList<>(Arrays.asList("Hackathon", "Robo-Wars", "Code Debugging", "Technical Quiz", "Paper Presentation"));

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        listView = view.findViewById(R.id.eventsListView);

        EventAdapter adapter = new EventAdapter(getContext(), events);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);

        return view;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select Action");
        menu.add(0, v.getId(), 0, "Bookmark");
        menu.add(0, v.getId(), 0, "Share");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (info == null) return false;

        String selectedEvent = events.get(info.position);

        if (item.getTitle().equals("Bookmark")) {
            Toast.makeText(getContext(), "Bookmarked: " + selectedEvent, Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getTitle().equals("Share")) {
            Toast.makeText(getContext(), "Sharing: " + selectedEvent, Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onContextItemSelected(item);
    }
}