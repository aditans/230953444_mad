package com.example.appbar_spinner_radio_checkbox_switch_seekbar_picker_listview_contextmenu_popupmenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Category> {

    public CategoryAdapter(@NonNull Context context, @NonNull List<Category> categories) {
        super(context, 0, categories);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.category_spinner_row, parent, false);
        }

        ImageView imageViewIcon = convertView.findViewById(R.id.image_view_icon);
        TextView textViewName = convertView.findViewById(R.id.text_view_name);

        Category currentItem = getItem(position);

        if (currentItem != null) {
            imageViewIcon.setImageResource(currentItem.getIconResId());
            textViewName.setText(currentItem.getName());
        }

        return convertView;
    }
}
