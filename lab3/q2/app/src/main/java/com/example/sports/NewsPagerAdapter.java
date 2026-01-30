package com.example.sports;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class NewsPagerAdapter extends FragmentStateAdapter {

    public NewsPagerAdapter(@NonNull FragmentActivity activity) {
        super(activity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return NewsFragment.newInstance("Top Stories");
            case 1:
                return NewsFragment.newInstance("Sport");
            case 2:
                return NewsFragment.newInstance("Entertainment");
            default:
                return NewsFragment.newInstance("Sport");
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
