package com.example.appbar_spinner_radio_checkbox_switch_seekbar_picker_listview_contextmenu_popupmenu;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerCategory;
    private RadioGroup rgTicketType, rgTshirtSize, rgMealPref, rgPaymentMethod;
    private RadioButton rbGeneral, rbVip, rbUpi, rbCard, rbPayAtVenue;
    private EditText etPromoCode, etUpiId, etCardNumber;
    private CheckBox cbTshirt, cbLunch, cbCertificate, cbVipLounge, cbEventKit;
    private SwitchCompat switchReminders;
    private Button btnPickSlot, btnRegister;
    private TextView tvSlotDisplay, tvGuestCount;
    private SeekBar sbGuests;
    private ListView lvRegistrations;

    private List<Category> categories;
    private List<Registration> registrationList = new ArrayList<>();
    private ArrayAdapter<Registration> registrationAdapter;

    private String selectedDate = "";
    private String selectedTime = "";
    private int categoryMaxGuests = 10;

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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Event Registration");
        }

        initViews();
        setupCategorySpinner();
        setupInterdependencies();
        setupRegistrationList();

        btnPickSlot.setOnClickListener(v -> showDatePicker());
        btnRegister.setOnClickListener(v -> validateAndRegister());
    }

    private void initViews() {
        spinnerCategory = findViewById(R.id.spinner_category);
        rgTicketType = findViewById(R.id.rg_ticket_type);
        rbGeneral = findViewById(R.id.rb_general);
        rbVip = findViewById(R.id.rb_vip);
        etPromoCode = findViewById(R.id.et_promo_code);

        cbTshirt = findViewById(R.id.cb_tshirt);
        rgTshirtSize = findViewById(R.id.rg_tshirt_size);
        cbLunch = findViewById(R.id.cb_lunch);
        rgMealPref = findViewById(R.id.rg_meal_pref);
        cbCertificate = findViewById(R.id.cb_certificate);
        cbVipLounge = findViewById(R.id.cb_vip_lounge);
        cbEventKit = findViewById(R.id.cb_event_kit);

        switchReminders = findViewById(R.id.switch_reminders);
        btnPickSlot = findViewById(R.id.btn_pick_slot);
        tvSlotDisplay = findViewById(R.id.tv_slot_display);

        tvGuestCount = findViewById(R.id.tv_guest_count);
        sbGuests = findViewById(R.id.sb_guests);

        rgPaymentMethod = findViewById(R.id.rg_payment_method);
        rbUpi = findViewById(R.id.rb_upi);
        rbCard = findViewById(R.id.rb_card);
        rbPayAtVenue = findViewById(R.id.rb_pay_at_venue);
        etUpiId = findViewById(R.id.et_upi_id);
        etCardNumber = findViewById(R.id.et_card_number);

        btnRegister = findViewById(R.id.btn_register);
        lvRegistrations = findViewById(R.id.lv_registrations);
    }

    private void setupCategorySpinner() {
        categories = new ArrayList<>();
        categories.add(new Category("Select Category", android.R.drawable.ic_menu_help));
        categories.add(new Category("Tech", android.R.drawable.ic_menu_manage));
        categories.add(new Category("Sports", android.R.drawable.ic_menu_directions));
        categories.add(new Category("Cultural", android.R.drawable.ic_menu_gallery));
        categories.add(new Category("Workshop", android.R.drawable.ic_menu_edit));

        CategoryAdapter adapter = new CategoryAdapter(this, categories);
        spinnerCategory.setAdapter(adapter);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category selected = categories.get(position);
                applyCategoryLogic(selected.getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void applyCategoryLogic(String category) {
        // Reset defaults
        rbVip.setEnabled(true);
        cbTshirt.setEnabled(true);
        cbLunch.setEnabled(true);
        cbCertificate.setEnabled(true);
        switchReminders.setEnabled(true);

        switch (category) {
            case "Tech":
                cbLunch.setChecked(false);
                cbLunch.setEnabled(false);
                Toast.makeText(this, "No catering for Tech events", Toast.LENGTH_SHORT).show();
                cbCertificate.setChecked(true);
                cbCertificate.setEnabled(false);
                Toast.makeText(this, "Certificate is mandatory for Tech events", Toast.LENGTH_SHORT).show();
                categoryMaxGuests = 2;
                break;
            case "Sports":
                rbVip.setEnabled(false);
                if (rbVip.isChecked()) {
                    rbGeneral.setChecked(true);
                    Toast.makeText(this, "VIP tickets not available for Sports", Toast.LENGTH_SHORT).show();
                }
                cbTshirt.setChecked(true);
                cbTshirt.setEnabled(false);
                Toast.makeText(this, "T-Shirt is mandatory for Sports", Toast.LENGTH_SHORT).show();
                cbCertificate.setChecked(false);
                cbCertificate.setEnabled(false);
                categoryMaxGuests = 5;
                break;
            case "Cultural":
                categoryMaxGuests = 10;
                break;
            case "Workshop":
                cbLunch.setChecked(true);
                cbLunch.setEnabled(false);
                Toast.makeText(this, "Lunch included in Workshop fee", Toast.LENGTH_SHORT).show();
                cbCertificate.setChecked(true);
                cbCertificate.setEnabled(false);
                Toast.makeText(this, "Certificate is mandatory for Workshops", Toast.LENGTH_SHORT).show();
                rbVip.setEnabled(false);
                if (rbVip.isChecked()) rbGeneral.setChecked(true);
                switchReminders.setChecked(true);
                switchReminders.setEnabled(false);
                Toast.makeText(this, "Reminders are mandatory for Workshop events", Toast.LENGTH_SHORT).show();
                categoryMaxGuests = 1;
                break;
            default:
                categoryMaxGuests = 10;
        }
        updateSeekBarMax();
    }

    private void setupInterdependencies() {
        rgTicketType.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_general) {
                etPromoCode.setVisibility(View.VISIBLE);
                cbVipLounge.setVisibility(View.GONE);
                rbPayAtVenue.setEnabled(true);
            } else if (checkedId == R.id.rb_vip) {
                etPromoCode.setVisibility(View.GONE);
                cbVipLounge.setVisibility(View.VISIBLE);
                rbPayAtVenue.setEnabled(false);
                if (rbPayAtVenue.isChecked()) {
                    rbUpi.setChecked(true);
                    Toast.makeText(this, "VIP tickets require online payment", Toast.LENGTH_SHORT).show();
                }
            }
            updateSeekBarMax();
        });

        cbTshirt.setOnCheckedChangeListener((buttonView, isChecked) -> {
            rgTshirtSize.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            if (!isChecked) {
                rgTshirtSize.clearCheck();
                if (cbEventKit.isChecked()) {
                    cbEventKit.setChecked(false);
                    Toast.makeText(this, "Event Kit requires T-Shirt", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cbLunch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            rgMealPref.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            if (!isChecked) rgMealPref.clearCheck();
        });

        cbCertificate.setOnClickListener(v -> {
            if (!cbCertificate.isChecked()) {
                new AlertDialog.Builder(this)
                        .setTitle("Warning")
                        .setMessage("Are you sure? Certificates cannot be reissued.")
                        .setPositiveButton("Confirm", (dialog, which) -> {})
                        .setNegativeButton("Cancel", (dialog, which) -> cbCertificate.setChecked(true))
                        .show();
            }
        });

        cbVipLounge.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) Toast.makeText(this, "VIP Lounge access added — ₹500 extra", Toast.LENGTH_SHORT).show();
        });

        cbEventKit.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (!cbTshirt.isChecked()) {
                    cbTshirt.setChecked(true);
                    Toast.makeText(this, "T-Shirt included in Event Kit", Toast.LENGTH_SHORT).show();
                }
            }
        });

        switchReminders.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                btnPickSlot.setBackgroundColor(Color.parseColor("#FFE0E0"));
            } else {
                btnPickSlot.setBackgroundColor(Color.LTGRAY);
                tvSlotDisplay.setText("No reminder set.");
                selectedDate = "";
                selectedTime = "";
            }
        });

        sbGuests.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateGuestText(progress, seekBar.getMax());
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        rgPaymentMethod.setOnCheckedChangeListener((group, checkedId) -> {
            etUpiId.setVisibility(checkedId == R.id.rb_upi ? View.VISIBLE : View.GONE);
            etCardNumber.setVisibility(checkedId == R.id.rb_card ? View.VISIBLE : View.GONE);
        });
    }

    private void updateSeekBarMax() {
        int max = categoryMaxGuests;
        if (rbVip.isChecked()) {
            max = max / 2;
            if (sbGuests.getProgress() > max) {
                sbGuests.setProgress(max);
                Toast.makeText(this, "Guest count adjusted for VIP limit.", Toast.LENGTH_SHORT).show();
            }
        }
        sbGuests.setMax(max);
        if (max == 0) {
            sbGuests.setEnabled(false);
            tvGuestCount.setText("Guests not allowed for this selection.");
        } else {
            sbGuests.setEnabled(true);
            updateGuestText(sbGuests.getProgress(), max);
        }
    }

    private void updateGuestText(int current, int max) {
        tvGuestCount.setText("Guests: " + current + " / " + max);
    }

    private void showDatePicker() {
        Calendar c = Calendar.getInstance();
        DatePickerDialog dpd = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            Calendar selected = Calendar.getInstance();
            selected.set(year, month, dayOfMonth);
            String category = ((Category) spinnerCategory.getSelectedItem()).getName();
            if (category.equals("Sports")) {
                int dayOfWeek = selected.get(Calendar.DAY_OF_WEEK);
                if (dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY) {
                    Toast.makeText(this, "Sports events are on weekends only", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
            showTimePicker();
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dpd.show();
    }

    private void showTimePicker() {
        Calendar c = Calendar.getInstance();
        new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            String category = ((Category) spinnerCategory.getSelectedItem()).getName();
            boolean valid = false;
            String range = "";
            if (category.equals("Tech") || category.equals("Workshop")) {
                if (hourOfDay >= 9 && hourOfDay < 18) valid = true;
                range = "9:00 AM – 6:00 PM";
            } else if (category.equals("Cultural")) {
                if (hourOfDay >= 16 && hourOfDay < 22) valid = true;
                range = "4:00 PM – 10:00 PM";
            } else if (category.equals("Sports")) {
                if (hourOfDay >= 6 && hourOfDay < 12) valid = true;
                range = "6:00 AM – 12:00 PM";
            } else {
                valid = true;
            }

            if (!valid) {
                Toast.makeText(this, "Valid range for " + category + ": " + range, Toast.LENGTH_SHORT).show();
            } else {
                String amPm = hourOfDay < 12 ? "AM" : "PM";
                int displayHour = hourOfDay > 12 ? hourOfDay - 12 : (hourOfDay == 0 ? 12 : hourOfDay);
                selectedTime = String.format("%02d:%02d %s", displayHour, minute, amPm);
                tvSlotDisplay.setText("Slot: " + selectedDate + " at " + selectedTime);
            }
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false).show();
    }

    private void setupRegistrationList() {
        registrationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, registrationList);
        lvRegistrations.setAdapter(registrationAdapter);
        registerForContextMenu(lvRegistrations);

        lvRegistrations.setOnItemClickListener((parent, view, position, id) -> {
            PopupMenu popup = new PopupMenu(this, view);
            popup.getMenuInflater().inflate(R.menu.registration_popup_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.menu_download_pdf) {
                    Toast.makeText(this, "Downloading confirmation PDF…", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (item.getItemId() == R.id.menu_share_reg) {
                    shareRegistration(registrationList.get(position));
                    return true;
                }
                return false;
            });
            popup.show();
        });
    }

    private void validateAndRegister() {
        if (spinnerCategory.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select an event category.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (rgTicketType.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select a ticket type.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (cbTshirt.isChecked() && rgTshirtSize.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select a T-Shirt size.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (cbLunch.isChecked() && rgMealPref.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select meal preference.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (switchReminders.isChecked() && (selectedDate.isEmpty() || selectedTime.isEmpty())) {
            Toast.makeText(this, "Please set your attendance slot for reminders.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (rgPaymentMethod.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select a payment method.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (rbUpi.isChecked() && etUpiId.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Enter your UPI ID.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (rbCard.isChecked() && etCardNumber.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Enter your card number.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (etPromoCode.getVisibility() == View.VISIBLE && !etPromoCode.getText().toString().isEmpty()) {
            if (!etPromoCode.getText().toString().matches("[A-Z0-9]+")) {
                Toast.makeText(this, "Invalid promo code format.", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        Registration reg = new Registration(
                ((Category) spinnerCategory.getSelectedItem()).getName(),
                ((RadioButton) findViewById(rgTicketType.getCheckedRadioButtonId())).getText().toString(),
                tvSlotDisplay.getText().toString(),
                sbGuests.getProgress(),
                getAddOnsText()
        );

        registrationList.add(reg);
        registrationAdapter.notifyDataSetChanged();

        Toast.makeText(this, "Registration Summary: " + reg.getCategory() + " registered!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, RegistrationSummaryActivity.class);
        intent.putExtra("registration", reg);
        startActivity(intent);
    }

    private String getAddOnsText() {
        StringBuilder sb = new StringBuilder();
        if (cbTshirt.isChecked()) sb.append("T-Shirt, ");
        if (cbLunch.isChecked()) sb.append("Lunch, ");
        if (cbCertificate.isChecked()) sb.append("Certificate, ");
        if (cbVipLounge.isChecked()) sb.append("VIP Lounge, ");
        if (cbEventKit.isChecked()) sb.append("Event Kit, ");
        String text = sb.toString();
        return text.isEmpty() ? "None" : text.substring(0, text.length() - 2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_my_registrations) {
            startActivity(new Intent(this, MyRegistrationsActivity.class));
            return true;
        } else if (id == R.id.menu_notifications) {
            startActivity(new Intent(this, NotificationsActivity.class));
            return true;
        } else if (id == R.id.menu_help) {
            startActivity(new Intent(this, HelpActivity.class));
            return true;
        } else if (id == R.id.menu_leaderboard) {
            startActivity(new Intent(this, LeaderboardActivity.class));
            return true;
        } else if (id == R.id.menu_share_app) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Register for amazing events with our app!");
            startActivity(Intent.createChooser(shareIntent, "Share App"));
            return true;
        } else if (id == R.id.menu_contact_organizer) {
            Intent dialIntent = new Intent(Intent.ACTION_DIAL);
            dialIntent.setData(Uri.parse("tel:1234567890"));
            startActivity(dialIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getItemId() == R.id.menu_edit) {
            prefillForm(registrationList.get(info.position));
            return true;
        } else if (item.getItemId() == R.id.menu_cancel) {
            Registration removed = registrationList.remove(info.position);
            registrationAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Registration cancelled.", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    private void prefillForm(Registration reg) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getName().equals(reg.getCategory())) {
                spinnerCategory.setSelection(i);
                break;
            }
        }
        if (reg.getTicketType().equals("General")) rbGeneral.setChecked(true);
        else rbVip.setChecked(true);
        sbGuests.setProgress(reg.getGuestCount());
        Toast.makeText(this, "Form pre-filled for editing.", Toast.LENGTH_SHORT).show();
    }

    private void shareRegistration(Registration reg) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Registration Summary:\n" + reg.toString());
        startActivity(Intent.createChooser(shareIntent, "Share Registration"));
    }
}
