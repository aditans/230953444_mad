package com.example.checkbox_radiobtn_grp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Declare Widgets
    private CheckBox cbPizza, cbBurger, cbPasta, cbJuice;
    private RadioGroup rgDelivery, rgPayment;
    private RadioButton rbHome, rbPickup, rbCash, rbUPI, rbCard;
    private Switch swMode;
    private SeekBar sbQuantity;
    private TextView tvQtyLabel;
    private ZoomButton zbOffer;
    private Button btnSubmit, btnWebsite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Initialize Widgets
        initWidgets();

        // 2. Logic Blocks
        setupToggleLogic();
        setupSeekBarLogic();
        setupZoomButtonLogic();
        setupSubmitLogic();
        setupImplicitIntent();
    }

    private void initWidgets() {
        cbPizza = findViewById(R.id.cbPizza);
        cbBurger = findViewById(R.id.cbBurger);
        cbPasta = findViewById(R.id.cbPasta);
        cbJuice = findViewById(R.id.cbJuice);

        swMode = findViewById(R.id.swMode);
        rgDelivery = findViewById(R.id.rgDelivery);
        rgPayment = findViewById(R.id.rgPayment);
        
        rbHome = findViewById(R.id.rbHome);
        rbPickup = findViewById(R.id.rbPickup);
        rbCash = findViewById(R.id.rbCash);
        rbUPI = findViewById(R.id.rbUPI);
        rbCard = findViewById(R.id.rbCard);

        sbQuantity = findViewById(R.id.sbQuantity);
        tvQtyLabel = findViewById(R.id.tvQtyLabel);

        zbOffer = findViewById(R.id.zbOffer);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnWebsite = findViewById(R.id.btnWebsite);

        // Initial state for RadioGroups
        rgPayment.setEnabled(false);
        for (int i = 0; i < rgPayment.getChildCount(); i++) {
            rgPayment.getChildAt(i).setEnabled(false);
        }
    }

    // --- Switch/Toggle Logic (B) ---
    private void setupToggleLogic() {
        swMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Delivery Mode ON: Enable Delivery, Disable Payment
                enableRadioGroup(rgDelivery, true);
                enableRadioGroup(rgPayment, false);
            } else {
                // Payment Mode OFF: Enable Payment, Disable Delivery
                enableRadioGroup(rgDelivery, false);
                enableRadioGroup(rgPayment, true);
            }
        });
    }

    private void enableRadioGroup(RadioGroup group, boolean enabled) {
        group.setEnabled(enabled);
        for (int i = 0; i < group.getChildCount(); i++) {
            group.getChildAt(i).setEnabled(enabled);
        }
    }

    // --- SeekBar Logic (C) ---
    private void setupSeekBarLogic() {
        sbQuantity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress < 1) progress = 1; // Ensure min 1
                tvQtyLabel.setText("Quantity: " + progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    // --- ZoomButton Logic (D) ---
    private void setupZoomButtonLogic() {
        zbOffer.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Special Offer Applied!", Toast.LENGTH_SHORT).show();
        });
    }

    // --- Submit Button Logic (E & F & G) ---
    private void setupSubmitLogic() {
        btnSubmit.setOnClickListener(v -> {
            StringBuilder details = new StringBuilder();
            int totalCost = 0;
            int qty = sbQuantity.getProgress();
            if (qty < 1) qty = 1;

            // Collect Food Items (A)
            if (cbPizza.isChecked()) { details.append("Pizza ($10), "); totalCost += 10; }
            if (cbBurger.isChecked()) { details.append("Burger ($5), "); totalCost += 5; }
            if (cbPasta.isChecked()) { details.append("Pasta ($8), "); totalCost += 8; }
            if (cbJuice.isChecked()) { details.append("Juice ($3), "); totalCost += 3; }

            if (details.length() == 0) {
                Toast.makeText(this, "Please select at least one item", Toast.LENGTH_SHORT).show();
                return;
            }

            int finalTotal = totalCost * qty;

            // Get Selected Radio Option (B)
            String selectedMode = "";
            if (swMode.isChecked()) {
                int id = rgDelivery.getCheckedRadioButtonId();
                if (id != -1) {
                    RadioButton rb = findViewById(id);
                    selectedMode = "Delivery: " + rb.getText();
                } else {
                    Toast.makeText(this, "Select Delivery Option", Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                int id = rgPayment.getCheckedRadioButtonId();
                if (id != -1) {
                    RadioButton rb = findViewById(id);
                    selectedMode = "Payment: " + rb.getText();
                } else {
                    Toast.makeText(this, "Select Payment Mode", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            String orderSummary = "Items: " + details.toString() + "\n" +
                                "Qty: " + qty + "\n" +
                                "Total: $" + finalTotal + "\n" +
                                "Mode: " + selectedMode;

            Toast.makeText(this, "Order Submitted Successfully", Toast.LENGTH_SHORT).show();

            // Lock UI (F)
            lockUI();

            // Pass data to Second Activity (G)
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            intent.putExtra("orderDetails", orderSummary);
            startActivity(intent);
        });
    }

    private void lockUI() {
        cbPizza.setEnabled(false);
        cbBurger.setEnabled(false);
        cbPasta.setEnabled(false);
        cbJuice.setEnabled(false);
        swMode.setEnabled(false);
        enableRadioGroup(rgDelivery, false);
        enableRadioGroup(rgPayment, false);
        sbQuantity.setEnabled(false);
        btnSubmit.setEnabled(false);
    }

    // --- Implicit Intent (H) ---
    private void setupImplicitIntent() {
        btnWebsite.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
            startActivity(browserIntent);
        });
    }
}
