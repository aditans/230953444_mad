package com.example.sports;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class NewsFragment extends Fragment {

    private static final String ARG_News = "News";

    public static NewsFragment newInstance(String News) {
        NewsFragment fragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_News, News);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news, container, false);
        TextView textView = view.findViewById(R.id.textView);

        if (getArguments() != null) {
            String News = getArguments().getString(ARG_News);
            textView.setText("Selected Page: " + News);
        }

        return view;
    }

}
