package com.example.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    TextView tvDisplay;
    String currentInput = "";
    double firstValue = 0;
    String operator = "";
    void calculateResult() {
        double secondValue = Double.parseDouble(currentInput);
        double result = 0;

        switch (operator) {
            case "+":
                result = firstValue + secondValue;
                break;
            case "-":
                result = firstValue - secondValue;
                break;
            case "*":
                result = firstValue * secondValue;
                break;
            case "/":
                if (secondValue != 0)
                    result = firstValue / secondValue;
                else
                    tvDisplay.setText("Error");
                return;
        }

        tvDisplay.setText(String.valueOf(result));
        currentInput = String.valueOf(result);
    }

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

        tvDisplay = findViewById(R.id.tvDisplay);

        int[] numberButtons = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        };


        View.OnClickListener numberListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button)  v;
                currentInput += b.getText().toString();
                tvDisplay.setText(currentInput);

            }
        };

        for (int id : numberButtons) {
            findViewById(id).setOnClickListener(numberListener);
        }

        View.OnClickListener opListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentInput.isEmpty()) {
                    Button b = (Button) v;
                    firstValue = Double.parseDouble(currentInput);
                    operator = b.getText().toString();
                    currentInput = "";   // clear input for second number
                }
            }
        };

        findViewById(R.id.btnAdd).setOnClickListener(opListener);
        findViewById(R.id.btnSubtract).setOnClickListener(opListener);
        findViewById(R.id.btnMultiply).setOnClickListener(opListener);
        findViewById(R.id.btnDivide).setOnClickListener(opListener);



        findViewById(R.id.btnEqual).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateResult();
            }
        });

        // Clear button
        findViewById(R.id.btnClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentInput = "";
                operator = "";
                firstValue = 0;
                tvDisplay.setText("0");
            }
        });

        Button btnNext = findViewById(R.id.btnNext);

        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
        });


        // Dot button
        findViewById(R.id.btnDot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentInput.contains(".")) {
                    currentInput += ".";
                    tvDisplay.setText(currentInput);
                }
            }
        });
    }



    }
