package com.example.deliveryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class MenuAdapter extends BaseAdapter {
    private Context context;
    private List<FoodItem> foodItems;

    public MenuAdapter(Context context, List<FoodItem> foodItems) {
        this.context = context;
        this.foodItems = foodItems;
    }

    @Override
    public int getCount() { return foodItems.size(); }

    @Override
    public Object getItem(int position) { return foodItems.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.menu_item, parent, false);
        }
        FoodItem item = foodItems.get(position);
        ImageView img = convertView.findViewById(R.id.imgFood);
        TextView name = convertView.findViewById(R.id.txtFoodName);
        TextView price = convertView.findViewById(R.id.txtFoodPrice);
        TextView quantity = convertView.findViewById(R.id.txtQuantity);
        Button btnPlus = convertView.findViewById(R.id.btnPlus);
        Button btnMinus = convertView.findViewById(R.id.btnMinus);

        img.setImageResource(item.getImageResId());
        name.setText(item.getName());
        price.setText("$" + item.getPrice());
        quantity.setText(String.valueOf(item.getQuantity()));

        btnPlus.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            notifyDataSetChanged();
        });

        btnMinus.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() - 1);
            notifyDataSetChanged();
        });

        return convertView;
    }
}
