package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return DayFragment.newInstance("Monday");
            case 1:
                return DayFragment.newInstance("Tuesday");
            case 2:
                return DayFragment.newInstance("Wednesday");
            default:
                return DayFragment.newInstance("Day");
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
