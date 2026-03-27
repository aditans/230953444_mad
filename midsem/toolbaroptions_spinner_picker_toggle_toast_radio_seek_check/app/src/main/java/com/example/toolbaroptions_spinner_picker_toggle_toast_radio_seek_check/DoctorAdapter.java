package com.example.toolbaroptions_spinner_picker_toggle_toast_radio_seek_check;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

public class DoctorAdapter extends ArrayAdapter<Doctor> {
    public DoctorAdapter(@NonNull Context context, @NonNull List<Doctor> doctors) {
        super(context, 0, doctors);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createView(position, convertView, parent);
    }

    private View createView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item_doctor, parent, false);
        }

        Doctor doctor = getItem(position);
        if (doctor != null) {
            TextView name = convertView.findViewById(R.id.tv_doctor_name);
            TextView specialization = convertView.findViewById(R.id.tv_doctor_specialization);
            name.setText(doctor.getName());
            specialization.setText(doctor.getSpecialization());
        }

        return convertView;
    }
}