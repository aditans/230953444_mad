package com.example.lab1;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.content.Intent;

import com.google.android.material.snackbar.Snackbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;




public class MainActivity extends AppCompatActivity {

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
        Button loginBtn = findViewById(R.id.button2);
        Button signinBtn = findViewById(R.id.button3);
        View rootView = findViewById(R.id.main);
        EditText usrbox = findViewById(R.id.editTextText);
        EditText emailbox = findViewById(R.id.editTextTextEmailAddress);
        EditText pwdbox = findViewById(R.id.editTextTextPassword);
        CheckBox cb = findViewById(R.id.checkBox);


        loginBtn.setOnClickListener(v -> {
            if (!cb.isChecked()){
                Snackbar.make(rootView, "Please accept the terms & conditions", Snackbar.LENGTH_SHORT)
                        .show();

            }

            else if (usrbox.getText().toString().equals("username")  & emailbox.getText().toString().equals("abc@gmail.com") & pwdbox.getText().toString().equals("password") ){
                Snackbar.make(rootView, "Login successful!", Snackbar.LENGTH_SHORT)
                        .show();
                startActivity(new Intent(MainActivity.this, LoadingActivity.class));

            }
            else{
                Snackbar.make(rootView, "Invalid Credentials", Snackbar.LENGTH_SHORT)
                        .show();
            }

        });
        signinBtn.setOnClickListener(v -> {
            usrbox.setText("");
            emailbox.setText("");pwdbox.setText("");


            Snackbar.make(rootView, "text reset!", Snackbar.LENGTH_SHORT)
                    .show();

        });

    }
}