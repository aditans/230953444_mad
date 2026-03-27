package com.example.appbar_options_menu_list_toast_toggle_radiobtn_grp_spinner_picker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class OrderAdapter extends ArrayAdapter<OrderItem> {
    private List<OrderItem> orderItems;
    private Context context;

    public OrderAdapter(@NonNull Context context, @NonNull List<OrderItem> objects) {
        super(context, 0, objects);
        this.orderItems = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_order, parent, false);
        }

        OrderItem item = getItem(position);
        TextView tvDetails = convertView.findViewById(R.id.tvOrderItemDetails);
        Button btnMore = convertView.findViewById(R.id.btnMore);

        if (item != null) {
            tvDetails.setText(item.toString());
        }

        btnMore.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(context, btnMore);
            popup.getMenu().add("Duplicate Item");
            popup.getMenu().add("Add Note");
            popup.setOnMenuItemClickListener(menuItem -> {
                if (menuItem.getTitle().equals("Duplicate Item")) {
                    OrderItem duplicate = new OrderItem(item.getName(), item.getPortion(), item.getSpiceLevel(), item.getAddOns(), item.isPriority());
                    orderItems.add(duplicate);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Item duplicated", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (menuItem.getTitle().equals("Add Note")) {
                    Toast.makeText(context, "Note added", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            });
            popup.show();
        });

        return convertView;
    }
}