package com.example.techfestpro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.ToggleButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class RegisterFragment extends Fragment {

    private EditText etName;
    private Spinner spinnerCategory;
    private RadioGroup rgStudentType;
    private RadioButton rbInternal, rbExternal;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private ToggleButton toggleAccommodation;
    private Button btnSubmit;
    private ImageButton btnHelp;

    private SharedPreferences sharedPreferences;
    private DatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        etName = view.findViewById(R.id.etName);
        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        rgStudentType = view.findViewById(R.id.rgStudentType);
        rbInternal = view.findViewById(R.id.rbInternal);
        rbExternal = view.findViewById(R.id.rbExternal);
        datePicker = view.findViewById(R.id.datePicker);
        timePicker = view.findViewById(R.id.timePicker);
        toggleAccommodation = view.findViewById(R.id.toggleAccommodation);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnHelp = view.findViewById(R.id.btnHelp);

        dbHelper = new DatabaseHelper(getContext());
        sharedPreferences = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        setupSpinner();
        loadSavedData();

        btnSubmit.setOnClickListener(v -> submitForm());
        btnHelp.setOnClickListener(this::showPopupMenu);

        return view;
    }

    private void setupSpinner() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Hackathon", android.R.drawable.ic_menu_edit));
        categories.add(new Category("Robo-Wars", android.R.drawable.ic_menu_manage));
        categories.add(new Category("Code Debugging", android.R.drawable.ic_menu_view));

        CategoryAdapter adapter = new CategoryAdapter(getContext(), categories);
        spinnerCategory.setAdapter(adapter);
    }

    private void loadSavedData() {
        String name = sharedPreferences.getString("name", "");
        String type = sharedPreferences.getString("type", "");

        etName.setText(name);
        if (type.equals("Internal")) {
            rbInternal.setChecked(true);
        } else if (type.equals("External")) {
            rbExternal.setChecked(true);
        }
    }

    private void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", etName.getText().toString());
        String type = "";
        if (rbInternal.isChecked()) type = "Internal";
        else if (rbExternal.isChecked()) type = "External";
        editor.putString("type", type);
        editor.apply();
    }

    private void submitForm() {
        String name = etName.getText().toString();
        Category selectedCategory = (Category) spinnerCategory.getSelectedItem();
        String category = selectedCategory != null ? selectedCategory.getName() : "";

        String type = "";
        if (rbInternal.isChecked()) type = "Internal";
        else if (rbExternal.isChecked()) type = "External";

        String arrival = datePicker.getYear() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getDayOfMonth() + " " +
                timePicker.getHour() + ":" + timePicker.getMinute();

        String accommodation = toggleAccommodation.getText().toString();

        if (dbHelper.insertRegistration(name, category, type, arrival, accommodation)) {
            saveData();
            Toast.makeText(getContext(), "Registration Successful!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), ConfirmationActivity.class);
            intent.putExtra("name", name);
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "Registration Failed!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showPopupMenu(View v) {
        PopupMenu popup = new PopupMenu(getContext(), v);
        popup.getMenu().add("View Registration Rules");
        popup.getMenu().add("Clear Form");

        popup.setOnMenuItemClickListener(item -> {
            if (item.getTitle().equals("View Registration Rules")) {
                Toast.makeText(getContext(), "Displaying Rules...", Toast.LENGTH_SHORT).show();
                return true;
            } else if (item.getTitle().equals("Clear Form")) {
                clearForm();
                return true;
            }
            return false;
        });
        popup.show();
    }

    private void clearForm() {
        etName.setText("");
        rgStudentType.clearCheck();
        toggleAccommodation.setChecked(false);
    }
}