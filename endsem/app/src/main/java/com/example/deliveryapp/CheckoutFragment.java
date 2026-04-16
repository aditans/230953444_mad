package com.example.deliveryapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CheckoutFragment extends Fragment {
    private TextView txtSelectedItems;
    private Spinner spinnerZone;
    private RadioGroup rgDeliveryType, rgPaymentMethod;
    private CheckBox cbCutlery;
    private Switch switchCutlery;
    private TimePicker timePicker;
    private DatabaseHelper dbHelper;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);

        txtSelectedItems = view.findViewById(R.id.txtSelectedItems);
        spinnerZone = view.findViewById(R.id.spinnerZone);
        rgDeliveryType = view.findViewById(R.id.rgDeliveryType);
        rgPaymentMethod = view.findViewById(R.id.rgPaymentMethod);
        cbCutlery = view.findViewById(R.id.cbCutlery);
        switchCutlery = view.findViewById(R.id.switchCutlery);
        timePicker = view.findViewById(R.id.timePicker);
        Button btnPlaceOrder = view.findViewById(R.id.btnPlaceOrder);

        dbHelper = new DatabaseHelper(getContext());
        sharedPreferences = getActivity().getSharedPreferences("BiteRunPrefs", Context.MODE_PRIVATE);

        String[] zones = {"North Campus", "South Campus", "East Campus", "West Campus"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, zones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerZone.setAdapter(adapter);

        int lastZone = sharedPreferences.getInt("lastZone", 0);
        spinnerZone.setSelection(lastZone);

        updateSelectedItemsDisplay();

        btnPlaceOrder.setOnClickListener(v -> {
            StringBuilder itemsBuilder = new StringBuilder();
            for (FoodItem item : MainActivity.foodList) {
                if (item.getQuantity() > 0) {
                    itemsBuilder.append(item.getName()).append(" (x").append(item.getQuantity()).append("), ");
                }
            }
            String items = itemsBuilder.toString();
            if (items.isEmpty()) {
                Toast.makeText(getContext(), "Please select items from the menu first", Toast.LENGTH_SHORT).show();
                return;
            }

            int selectedZonePos = spinnerZone.getSelectedItemPosition();
            sharedPreferences.edit().putInt("lastZone", selectedZonePos).apply();

            int typeId = rgDeliveryType.getCheckedRadioButtonId();
            int paymentId = rgPaymentMethod.getCheckedRadioButtonId();

            if (typeId == -1 || paymentId == -1) {
                Toast.makeText(getContext(), "Please select delivery type and payment method", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton rbType = view.findViewById(typeId);
            RadioButton rbPayment = view.findViewById(paymentId);
            String time = timePicker.getHour() + ":" + timePicker.getMinute();
            int cutlery = (cbCutlery.isChecked() || switchCutlery.isChecked()) ? 1 : 0;

            long id = dbHelper.insertOrder(items, zones[selectedZonePos], rbType.getText().toString(), rbPayment.getText().toString(), cutlery, time);
            if (id != -1) {
                Toast.makeText(getContext(), "Order Placed Successfully!", Toast.LENGTH_SHORT).show();
                // Reset quantities
                for (FoodItem item : MainActivity.foodList) item.setQuantity(0);
                updateSelectedItemsDisplay();
            }
        });

        return view;
    }

    private void updateSelectedItemsDisplay() {
        StringBuilder sb = new StringBuilder();
        for (FoodItem item : MainActivity.foodList) {
            if (item.getQuantity() > 0) {
                sb.append(item.getName()).append(" x").append(item.getQuantity()).append("\n");
            }
        }
        if (sb.length() == 0) {
            txtSelectedItems.setText("No items selected");
        } else {
            txtSelectedItems.setText(sb.toString().trim());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateSelectedItemsDisplay();
    }
}
