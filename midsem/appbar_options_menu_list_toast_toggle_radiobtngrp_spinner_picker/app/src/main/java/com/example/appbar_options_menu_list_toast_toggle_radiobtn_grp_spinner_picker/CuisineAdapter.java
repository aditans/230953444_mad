package com.example.appbar_options_menu_list_toast_toggle_radiobtn_grp_spinner_picker;

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

public class CuisineAdapter extends ArrayAdapter<Cuisine> {
    public CuisineAdapter(@NonNull Context context, @NonNull List<Cuisine> cuisines) {
        super(context, 0, cuisines);
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.ivCuisine);
        TextView textView = convertView.findViewById(R.id.tvCuisineName);

        Cuisine currentCuisine = getItem(position);

        if (currentCuisine != null) {
            imageView.setImageResource(currentCuisine.getImageResId());
            textView.setText(currentCuisine.getName());
        }

        return convertView;
    }
}