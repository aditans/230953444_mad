package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class DayFragment extends Fragment {

    private static final String ARG_DAY = "day";

    public static DayFragment newInstance(String day) {
        DayFragment fragment = new DayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DAY, day);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_day, container, false);

        TextView emptyView = view.findViewById(R.id.emptyView);

        if (getArguments() != null) {
            String day = getArguments().getString(ARG_DAY);
            emptyView.setText("No data for " + day);
        }

        return view;
    }
}
