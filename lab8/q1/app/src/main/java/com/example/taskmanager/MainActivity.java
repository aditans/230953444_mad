package com.example.taskmanager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etTaskName, etDueDate;
    private Spinner spinnerPriority;
    private Button btnSave;
    private ListView listViewTasks;
    private DatabaseHelper dbHelper;
    private TaskAdapter adapter;
    private List<Task> taskList;
    private Task taskToEdit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        View mainView = findViewById(R.id.main_layout);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        dbHelper = new DatabaseHelper(this);
        etTaskName = findViewById(R.id.etTaskName);
        etDueDate = findViewById(R.id.etDueDate);
        spinnerPriority = findViewById(R.id.spinnerPriority);
        btnSave = findViewById(R.id.btnSave);
        listViewTasks = findViewById(R.id.listViewTasks);

        etDueDate.setOnClickListener(v -> showDatePicker());

        btnSave.setOnClickListener(v -> {
            String name = etTaskName.getText().toString().trim();
            String dueDate = etDueDate.getText().toString().trim();
            String priority = spinnerPriority.getSelectedItem().toString();

            if (name.isEmpty() || dueDate.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (taskToEdit == null) {
                Task newTask = new Task(name, dueDate, priority);
                dbHelper.addTask(newTask);
                Toast.makeText(MainActivity.this, "Task Saved", Toast.LENGTH_SHORT).show();
            } else {
                taskToEdit.setName(name);
                taskToEdit.setDueDate(dueDate);
                taskToEdit.setPriority(priority);
                dbHelper.updateTask(taskToEdit);
                Toast.makeText(MainActivity.this, "Task Updated", Toast.LENGTH_SHORT).show();
                taskToEdit = null;
                btnSave.setText(R.string.save_task);
            }

            clearFields();
            refreshTaskList();
        });

        refreshTaskList();
    }

    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String date = year1 + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                    etDueDate.setText(date);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void refreshTaskList() {
        taskList = dbHelper.getAllTasks();
        adapter = new TaskAdapter();
        listViewTasks.setAdapter(adapter);
    }

    private void clearFields() {
        etTaskName.setText("");
        etDueDate.setText("");
        spinnerPriority.setSelection(0);
    }

    private class TaskAdapter extends BaseAdapter {
        @Override
        public int getCount() { return taskList.size(); }
        @Override
        public Object getItem(int position) { return taskList.get(position); }
        @Override
        public long getItemId(int position) { return taskList.get(position).getId(); }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.task_item, parent, false);
            }

            Task task = taskList.get(position);
            TextView tvName = convertView.findViewById(R.id.tvTaskName);
            TextView tvDate = convertView.findViewById(R.id.tvDueDate);
            TextView tvPriority = convertView.findViewById(R.id.tvPriority);

            tvName.setText(task.getName());
            tvDate.setText(task.getDueDate());
            tvPriority.setText(task.getPriority());

            convertView.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Options")
                        .setItems(new String[]{"Edit", "Delete"}, (dialog, which) -> {
                            if (which == 0) { // Edit
                                taskToEdit = task;
                                etTaskName.setText(task.getName());
                                etDueDate.setText(task.getDueDate());
                                btnSave.setText("Update Task");
                                for (int i = 0; i < spinnerPriority.getCount(); i++) {
                                    if (spinnerPriority.getItemAtPosition(i).toString().equals(task.getPriority())) {
                                        spinnerPriority.setSelection(i);
                                        break;
                                    }
                                }
                            } else { // Delete
                                dbHelper.deleteTask(task.getId());
                                refreshTaskList();
                                Toast.makeText(MainActivity.this, "Task Deleted", Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.show();
            });

            return convertView;
        }
    }
}
