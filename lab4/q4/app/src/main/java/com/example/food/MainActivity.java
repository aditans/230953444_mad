package com.example.food;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.widget.CheckBox;
public class MainActivity extends AppCompatActivity {

    CheckBox cbPizza, cbBurger, cbPasta, cbCoffee;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        cbPizza = findViewById(R.id.cbPizza);
        cbBurger = findViewById(R.id.cbBurger);
        cbPasta = findViewById(R.id.cbPasta);
        cbCoffee = findViewById(R.id.cbCoffee);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(v -> {

            StringBuilder orderDetails = new StringBuilder();
            int totalCost = 0;

            if (cbPizza.isChecked()) {
                orderDetails.append("Pizza - ₹150\n");
                totalCost += 150;
            }

            if (cbBurger.isChecked()) {
                orderDetails.append("Burger - ₹100\n");
                totalCost += 100;
            }

            if (cbPasta.isChecked()) {
                orderDetails.append("Pasta - ₹120\n");
                totalCost += 120;
            }

            if (cbCoffee.isChecked()) {
                orderDetails.append("Coffee - ₹50\n");
                totalCost += 50;
            }

            // Disable all checkboxes after submit
            cbPizza.setEnabled(false);
            cbBurger.setEnabled(false);
            cbPasta.setEnabled(false);
            cbCoffee.setEnabled(false);
            btnSubmit.setEnabled(false);

            Intent intent = new Intent(MainActivity.this, order_summary.class);
            intent.putExtra("order", orderDetails.toString());
            intent.putExtra("total", totalCost);
            startActivity(intent);
        });
    }
}