package com.example.deliveryapp;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateOrderActivity extends AppCompatActivity {
    private Spinner spinnerZone;
    private RadioGroup rgType, rgPayment;
    private CheckBox cbCutlery;
    private TimePicker timePicker;
    private DatabaseHelper dbHelper;
    private int orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_order);

        orderId = getIntent().getIntExtra("order_id", -1);
        dbHelper = new DatabaseHelper(this);

        spinnerZone = findViewById(R.id.updateSpinnerZone);
        rgType = findViewById(R.id.updateRgDeliveryType);
        rgPayment = findViewById(R.id.updateRgPaymentMethod);
        cbCutlery = findViewById(R.id.updateCbCutlery);
        timePicker = findViewById(R.id.updateTimePicker);
        Button btnSave = findViewById(R.id.btnSaveUpdate);

        String[] zones = {"North Campus", "South Campus", "East Campus", "West Campus"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, zones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerZone.setAdapter(adapter);

        loadOrderData();

        btnSave.setOnClickListener(v -> {
            String zone = spinnerZone.getSelectedItem().toString();
            RadioButton rbType = findViewById(rgType.getCheckedRadioButtonId());
            RadioButton rbPayment = findViewById(rgPayment.getCheckedRadioButtonId());
            int cutlery = cbCutlery.isChecked() ? 1 : 0;
            String time = timePicker.getHour() + ":" + timePicker.getMinute();

            dbHelper.updateOrder(orderId, zone, rbType.getText().toString(), rbPayment.getText().toString(), cutlery, time);
            Toast.makeText(this, "Order Updated", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void loadOrderData() {
        Cursor cursor = dbHelper.getOrderById(orderId);
        if (cursor != null && cursor.moveToFirst()) {
            String zone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DELIVERY_ZONE));
            String type = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DELIVERY_TYPE));
            String payment = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PAYMENT_METHOD));
            int cutlery = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CUTLERY));
            String time = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TIME));

            // Set Spinner
            for (int i = 0; i < spinnerZone.getCount(); i++) {
                if (spinnerZone.getItemAtPosition(i).toString().equals(zone)) {
                    spinnerZone.setSelection(i);
                    break;
                }
            }

            // Set RadioGroups
            if ("Standard Delivery".equals(type)) rgType.check(R.id.updateRbStandard);
            else rgType.check(R.id.updateRbExpress);

            if ("Cash on Delivery".equals(payment)) rgPayment.check(R.id.updateRbCash);
            else if ("Credit Card".equals(payment)) rgPayment.check(R.id.updateRbCredit);
            else rgPayment.check(R.id.updateRbUPI);

            cbCutlery.setChecked(cutlery == 1);

            String[] timeParts = time.split(":");
            timePicker.setHour(Integer.parseInt(timeParts[0]));
            timePicker.setMinute(Integer.parseInt(timeParts[1]));
            cursor.close();
        }
    }
}
