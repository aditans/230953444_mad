package com.example.deliveryapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static List<FoodItem> foodList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (foodList.isEmpty()) {
            foodList.add(new FoodItem("Burger", 5.99, android.R.drawable.ic_menu_gallery));
            foodList.add(new FoodItem("Pizza", 8.99, android.R.drawable.ic_menu_gallery));
            foodList.add(new FoodItem("Pasta", 7.49, android.R.drawable.ic_menu_gallery));
        }

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager = findViewById(R.id.viewPager);

        viewPager.setAdapter(new ViewPagerAdapter(this));

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0: tab.setText("Menu"); break;
                case 1: tab.setText("Checkout"); break;
                case 2: tab.setText("Order History"); break;
            }
        }).attach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 101, 0, "Settings");
        menu.add(0, 102, 0, "Call Support");
        return true;
        // INSERT INTO orders (items, delivery_zone, delivery_type, payment_method, cutlery, time, status)
        //VALUES ('Burger x2, Pizza x1', 'North Campus', 'Express Delivery', 'UPI', 1, '14:30', 'Pending');
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == 101) {
            Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == 102) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:1234567890"));
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private static class ViewPagerAdapter extends FragmentStateAdapter {
        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0: return new MenuFragment();
                case 1: return new CheckoutFragment();
                case 2: return new HistoryFragment();
                default: return new MenuFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}
