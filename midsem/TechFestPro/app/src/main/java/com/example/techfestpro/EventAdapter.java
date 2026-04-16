package com.example.techfestpro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class EventAdapter extends ArrayAdapter<String> {
    public EventAdapter(@NonNull Context context, @NonNull List<String> events) {
        super(context, 0, events);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_event, parent, false);
        }
        String event = getItem(position);
        TextView tvEventName = convertView.findViewById(R.id.tvEventName);
        if (event != null) {
            tvEventName.setText(event);
        }
        return convertView;
    }
}