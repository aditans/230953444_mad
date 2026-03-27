package com.example.appbar_options_menu_list_toast_toggle_radiobtn_grp_spinner_picker;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RadioGroup rgOrderType, rgPortionSize, rgPayment;
    private RadioButton rbDineIn, rbDelivery, rbTakeout, rbSmall, rbMedium, rbLarge, rbCash, rbCard, rbUPI;
    private EditText etAddress, etTableNumber, etCardNumber, etUPI;
    private CheckBox cbPackingCharges, cbCheese, cbSauce, cbGarlicBread, cbSpices;
    private Spinner spinnerCuisine;
    private SeekBar seekBarSpice;
    private TextView tvSpiceLevel, tvScheduledTime;
    private Button btnSchedule, btnAddItem, btnPlaceOrder;
    private ToggleButton tbPriority;
    private ListView listViewOrder;

    private List<OrderItem> orderItems = new ArrayList<>();
    private OrderAdapter orderAdapter;
    private String scheduledTimeStr = "";
    private Calendar selectedDateTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupToolbar();
        setupOrderTypeLogic();
        setupCuisineSpinner();
        setupPortionAndSpiceLogic();
        setupPaymentLogic();
        setupScheduleButton();
        setupOrderList();

        btnPlaceOrder.setOnClickListener(v -> validateAndPlaceOrder());

        handleIncomingIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIncomingIntent(intent);
    }

    private void handleIncomingIntent(Intent intent) {
        if (intent == null) return;
        if (intent.hasExtra("reorder")) {
            String reorderInfo = intent.getStringExtra("reorder");
            Toast.makeText(this, "Pre-filling for: " + reorderInfo, Toast.LENGTH_SHORT).show();
            // In a real app, parse reorderInfo and fill fields
        }
        if (intent.hasExtra("edit_item")) {
            OrderItem item = (OrderItem) intent.getSerializableExtra("edit_item");
            if (item != null) {
                // Pre-fill form with item details
                tbPriority.setChecked(item.isPriority());
                seekBarSpice.setProgress(item.getSpiceLevel());
                Toast.makeText(this, "Editing item: " + item.getName(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        rgOrderType = findViewById(R.id.rgOrderType);
        rbDineIn = findViewById(R.id.rbDineIn);
        rbDelivery = findViewById(R.id.rbDelivery);
        rbTakeout = findViewById(R.id.rbTakeout);
        etAddress = findViewById(R.id.etAddress);
        etTableNumber = findViewById(R.id.etTableNumber);
        cbPackingCharges = findViewById(R.id.cbPackingCharges);

        spinnerCuisine = findViewById(R.id.spinnerCuisine);
        cbCheese = findViewById(R.id.cbCheese);
        cbSauce = findViewById(R.id.cbSauce);
        cbGarlicBread = findViewById(R.id.cbGarlicBread);
        cbSpices = findViewById(R.id.cbSpices);

        rgPortionSize = findViewById(R.id.rgPortionSize);
        rbSmall = findViewById(R.id.rbSmall);
        rbMedium = findViewById(R.id.rbMedium);
        rbLarge = findViewById(R.id.rbLarge);
        tvSpiceLevel = findViewById(R.id.tvSpiceLevel);
        seekBarSpice = findViewById(R.id.seekBarSpice);

        rgPayment = findViewById(R.id.rgPayment);
        rbCash = findViewById(R.id.rbCash);
        rbCard = findViewById(R.id.rbCard);
        rbUPI = findViewById(R.id.rbUPI);
        etCardNumber = findViewById(R.id.etCardNumber);
        etUPI = findViewById(R.id.etUPI);

        btnSchedule = findViewById(R.id.btnSchedule);
        tvScheduledTime = findViewById(R.id.tvScheduledTime);
        tbPriority = findViewById(R.id.tbPriority);
        btnAddItem = findViewById(R.id.btnAddItem);
        listViewOrder = findViewById(R.id.listViewOrder);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Restaurant Order");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_view_menu) {
            startActivity(new Intent(this, MenuActivity.class));
            return true;
        } else if (id == R.id.menu_my_cart) {
            startActivity(new Intent(this, CartActivity.class));
            return true;
        } else if (id == R.id.menu_order_history) {
            startActivity(new Intent(this, OrderHistoryActivity.class));
            return true;
        } else if (id == R.id.menu_offers) {
            startActivity(new Intent(this, OffersActivity.class));
            return true;
        } else if (id == R.id.menu_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Try our Restaurant App!");
            startActivity(Intent.createChooser(shareIntent, "Share App via"));
            return true;
        } else if (id == R.id.menu_call) {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:1800123456"));
            startActivity(callIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupOrderTypeLogic() {
        rgOrderType.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbDineIn) {
                etAddress.setVisibility(View.GONE);
                cbPackingCharges.setChecked(false);
                cbPackingCharges.setEnabled(false);
                etTableNumber.setVisibility(View.VISIBLE);
                btnSchedule.setEnabled(false);

                // Portions logic
                rbLarge.setEnabled(false);
                if (rbLarge.isChecked()) {
                    rbMedium.setChecked(true);
                    Toast.makeText(this, "Large portion only for delivery/takeout", Toast.LENGTH_SHORT).show();
                }

                // Payment logic
                rbCash.setChecked(true);
                rbUPI.setEnabled(false);
            } else if (checkedId == R.id.rbDelivery) {
                etAddress.setVisibility(View.VISIBLE);
                cbPackingCharges.setChecked(true);
                cbPackingCharges.setEnabled(false); // cannot be unchecked
                etTableNumber.setVisibility(View.GONE);
                btnSchedule.setEnabled(true);

                rbLarge.setEnabled(true);
                rbUPI.setEnabled(true);
                rgPayment.clearCheck();
            } else if (checkedId == R.id.rbTakeout) {
                etAddress.setVisibility(View.GONE);
                cbPackingCharges.setEnabled(true);
                cbPackingCharges.setChecked(false);
                etTableNumber.setVisibility(View.GONE);
                btnSchedule.setEnabled(true);

                rbLarge.setEnabled(true);
                rbUPI.setEnabled(true);
                rgPayment.clearCheck();
            }
        });
    }

    private void setupCuisineSpinner() {
        List<Cuisine> cuisines = new ArrayList<>();
        cuisines.add(new Cuisine("Indian", R.mipmap.ic_launcher));
        cuisines.add(new Cuisine("Chinese", R.mipmap.ic_launcher));
        cuisines.add(new Cuisine("Italian", R.mipmap.ic_launcher));
        cuisines.add(new Cuisine("Mexican", R.mipmap.ic_launcher));

        CuisineAdapter adapter = new CuisineAdapter(this, cuisines);
        spinnerCuisine.setAdapter(adapter);

        spinnerCuisine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = cuisines.get(position).getName();
                enableAllCheckBoxes();

                if (selected.equals("Indian")) {
                    disableCheckBox(cbGarlicBread, "Garlic Bread not available for Indian cuisine");
                } else if (selected.equals("Italian")) {
                    disableCheckBox(cbSpices, "Extra Spices not available for Italian cuisine");
                } else if (selected.equals("Chinese")) {
                    disableCheckBox(cbCheese, "Extra Cheese not available for Chinese cuisine");
                    disableCheckBox(cbGarlicBread, "Garlic Bread not available for Chinese cuisine");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void enableAllCheckBoxes() {
        cbCheese.setEnabled(true);
        cbSauce.setEnabled(true);
        cbGarlicBread.setEnabled(true);
        cbSpices.setEnabled(true);
    }

    private void disableCheckBox(CheckBox cb, String message) {
        if (cb.isEnabled()) {
            cb.setChecked(false);
            cb.setEnabled(false);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    private void setupPortionAndSpiceLogic() {
        rgPortionSize.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbSmall) {
                seekBarSpice.setMax(2);
                if (seekBarSpice.getProgress() > 2) {
                    seekBarSpice.setProgress(2);
                    Toast.makeText(this, "Spice level reduced for small portion.", Toast.LENGTH_SHORT).show();
                }
            } else if (checkedId == R.id.rbMedium) {
                seekBarSpice.setMax(4);
            } else if (checkedId == R.id.rbLarge) {
                seekBarSpice.setMax(5);
            }
            updateSpiceText();
        });

        seekBarSpice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateSpiceText();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void updateSpiceText() {
        tvSpiceLevel.setText("Spice Level: " + seekBarSpice.getProgress() + " / " + seekBarSpice.getMax());
    }

    private void setupPaymentLogic() {
        rgPayment.setOnCheckedChangeListener((group, checkedId) -> {
            etCardNumber.setVisibility(checkedId == R.id.rbCard ? View.VISIBLE : View.GONE);
            etUPI.setVisibility(checkedId == R.id.rbUPI ? View.VISIBLE : View.GONE);
        });
    }

    private void setupScheduleButton() {
        btnSchedule.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            DatePickerDialog datePicker = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                selectedDateTime.set(Calendar.YEAR, year);
                selectedDateTime.set(Calendar.MONTH, month);
                selectedDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TimePickerDialog timePicker = new TimePickerDialog(this, (view1, hourOfDay, minute) -> {
                    if (validateTime(hourOfDay)) {
                        selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        selectedDateTime.set(Calendar.MINUTE, minute);
                        String amPm = hourOfDay >= 12 ? "PM" : "AM";
                        int displayHour = hourOfDay > 12 ? hourOfDay - 12 : (hourOfDay == 0 ? 12 : hourOfDay);
                        scheduledTimeStr = dayOfMonth + "/" + (month + 1) + "/" + year + " at " + String.format("%02d:%02d ", displayHour, minute) + amPm;
                        tvScheduledTime.setText("Scheduled: " + scheduledTimeStr);
                    }
                }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false);
                timePicker.show();

            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            datePicker.getDatePicker().setMinDate(System.currentTimeMillis());
            datePicker.show();
        });
    }

    private boolean validateTime(int hour) {
        if (rbDelivery.isChecked()) {
            if (hour < 10 || hour >= 23) {
                Toast.makeText(this, "Delivery allowed only 10:00 AM – 11:00 PM", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (rbTakeout.isChecked()) {
            if (hour < 11 || hour >= 22) {
                Toast.makeText(this, "Takeout allowed only 11:00 AM – 10:00 PM", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private void setupOrderList() {
        orderAdapter = new OrderAdapter(this, orderItems);
        listViewOrder.setAdapter(orderAdapter);
        registerForContextMenu(listViewOrder);

        btnAddItem.setOnClickListener(v -> {
            Cuisine selectedCuisine = (Cuisine) spinnerCuisine.getSelectedItem();
            String cuisineName = selectedCuisine != null ? selectedCuisine.getName() : "Unknown";
            int selectedPortionId = rgPortionSize.getCheckedRadioButtonId();
            if (selectedPortionId == -1) {
                Toast.makeText(this, "Select a portion size", Toast.LENGTH_SHORT).show();
                return;
            }
            String portion = ((RadioButton) findViewById(selectedPortionId)).getText().toString();
            int spice = seekBarSpice.getProgress();
            StringBuilder addOns = new StringBuilder();
            if (cbCheese.isChecked()) addOns.append("Cheese ");
            if (cbSauce.isChecked()) addOns.append("Sauce ");
            if (cbGarlicBread.isChecked()) addOns.append("GarlicBread ");
            if (cbSpices.isChecked()) addOns.append("Spices ");

            boolean isPriority = tbPriority.isChecked();
            if (isPriority) {
                Toast.makeText(this, "Priority charges of ₹50 will be added.", Toast.LENGTH_SHORT).show();
            }

            orderItems.add(new OrderItem(cuisineName, portion, spice, addOns.toString().trim(), isPriority));
            orderAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select Action");
        menu.add(0, v.getId(), 0, "Edit");
        menu.add(0, v.getId(), 0, "Remove");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (info == null) return false;
        if (item.getTitle().equals("Edit")) {
            OrderItem orderItem = orderItems.get(info.position);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("edit_item", orderItem);
            startActivity(intent);
            return true;
        } else if (item.getTitle().equals("Remove")) {
            String removedItem = orderItems.get(info.position).getName();
            orderItems.remove(info.position);
            orderAdapter.notifyDataSetChanged();
            Toast.makeText(this, removedItem + " removed", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    private void validateAndPlaceOrder() {
        if (orderItems.isEmpty()) {
            Toast.makeText(this, "Add at least one item.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (rgOrderType.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Select an order type.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (rbDelivery.isChecked() && etAddress.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Enter delivery address.", Toast.LENGTH_SHORT).show();
            return;
        }
        if ((rbDelivery.isChecked() || rbTakeout.isChecked()) && scheduledTimeStr.isEmpty()) {
            Toast.makeText(this, "Please schedule a time.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (rgPortionSize.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Select a portion size.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (rgPayment.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Select a payment method.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (rbCard.isChecked() && etCardNumber.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Enter card number.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (rbUPI.isChecked() && etUPI.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Enter UPI ID.", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, OrderSummaryActivity.class);
        intent.putExtra("items", (ArrayList<OrderItem>) orderItems);
        intent.putExtra("type", ((RadioButton) findViewById(rgOrderType.getCheckedRadioButtonId())).getText().toString());
        intent.putExtra("scheduled", scheduledTimeStr);
        startActivity(intent);
    }
}