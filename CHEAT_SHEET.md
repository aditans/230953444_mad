# 📱 Android MAD Lab — Complete Cheat Sheet
> ICT 3268 | MIT Manipal | Lab Exam Reference

---

## TABLE OF CONTENTS
1. [Activity & Lifecycle](#1-activity--lifecycle)
2. [Layouts](#2-layouts)
3. [TextView, ImageView, Button](#3-textview-imageview-button)
4. [ListView & GridView (with Adapter)](#4-listview--gridview-with-adapter)
5. [TabLayout with ViewPager](#5-tablayout-with-viewpager)
6. [TableLayout](#6-tablelayout)
7. [Input Controls](#7-input-controls)
   - Button, SeekBar, CheckBox, ToggleButton, RadioButton, Switch
8. [Intents (Explicit & Implicit)](#8-intents)
9. [Toast](#9-toast)
10. [Spinners](#10-spinners)
11. [Pickers (Date & Time)](#11-pickers)
12. [Option Menu & App Bar](#12-option-menu--app-bar)
13. [Context Menu (Floating)](#13-context-menu-floating)
14. [Contextual Action Mode](#14-contextual-action-mode)
15. [Popup Menu](#15-popup-menu)
16. [SQLite Database](#16-sqlite-database)
17. [Shared Preferences](#17-shared-preferences)

---

## 1. Activity & Lifecycle

### What is an Activity?
A single screen with a UI. Declared in `AndroidManifest.xml`.

### Lifecycle Methods
| Method | When it's called |
|---|---|
| `onCreate()` | Activity first created, set up UI here |
| `onStart()` | Activity becomes visible |
| `onResume()` | Activity starts interacting with user |
| `onPause()` | Another activity comes to foreground |
| `onStop()` | Activity no longer visible |
| `onDestroy()` | Activity is being destroyed |

### MainActivity.java (Lifecycle demo)
```java
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Lifecycle", "onCreate called");
    }
    @Override
    protected void onStart()   { super.onStart();   Log.d("Lifecycle", "onStart"); }
    @Override
    protected void onResume()  { super.onResume();  Log.d("Lifecycle", "onResume"); }
    @Override
    protected void onPause()   { super.onPause();   Log.d("Lifecycle", "onPause"); }
    @Override
    protected void onStop()    { super.onStop();    Log.d("Lifecycle", "onStop"); }
    @Override
    protected void onDestroy() { super.onDestroy(); Log.d("Lifecycle", "onDestroy"); }
}
```

---

## 2. Layouts

### LinearLayout (vertical/horizontal)
```xml
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <TextView android:layout_width="wrap_content"
        android:layout_height="wrap_content" android:text="Hello" />
    <Button android:layout_width="wrap_content"
        android:layout_height="wrap_content" android:text="Click" />
</LinearLayout>
```

### RelativeLayout
```xml
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <EditText android:id="@+id/editText"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Type here" />
    <Button android:id="@+id/btnOk"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="OK"
        android:layout_below="@id/editText"
        android:layout_alignParentEnd="true" />
    <Button android:id="@+id/btnCancel"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Cancel"
        android:layout_below="@id/editText"
        android:layout_toStartOf="@id/btnOk" />
</RelativeLayout>
```

### ConstraintLayout
```xml
<androidx.constraintlayout.widget.ConstraintLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <TextView android:id="@+id/tv"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

---

## 3. TextView, ImageView, Button

### XML
```xml
<!-- TextView -->
<TextView
    android:id="@+id/textView"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Hello, World!"
    android:textSize="18sp"
    android:textColor="#FF0000" />

<!-- ImageView -->
<ImageView
    android:id="@+id/imageView"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:src="@drawable/sample_image" />

<!-- Button -->
<Button
    android:id="@+id/button"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Click Me" />
```

### MainActivity.java
```java
TextView textView = findViewById(R.id.textView);
Button button = findViewById(R.id.button);

button.setOnClickListener(view -> {
    textView.setText("Button Clicked!");
});
```

---

## 4. ListView & GridView (with Adapter)

### ListView XML
```xml
<ListView
    android:id="@+id/listView"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

### GridView XML
```xml
<GridView
    android:id="@+id/gridView"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:numColumns="3" />
```

### MainActivity.java
```java
// ListView with ArrayAdapter
ListView listView = findViewById(R.id.listView);
String[] items = {"Cricket", "Football", "Tennis", "Basketball"};
ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
    android.R.layout.simple_list_item_1, items);
listView.setAdapter(adapter);

// Click listener
listView.setOnItemClickListener((parent, view, position, id) -> {
    String selected = items[position];
    Toast.makeText(this, "Selected: " + selected, Toast.LENGTH_SHORT).show();
});

// GridView with ArrayAdapter
GridView gridView = findViewById(R.id.gridView);
String[] gridItems = {"A", "B", "C", "D", "E", "F"};
ArrayAdapter<String> gridAdapter = new ArrayAdapter<>(this,
    android.R.layout.simple_list_item_1, gridItems);
gridView.setAdapter(gridAdapter);
```

---

## 5. TabLayout with ViewPager

### Steps:
1. Add dependency in `build.gradle`: `implementation 'com.google.android.material:material:1.x.x'`
2. Define XML with TabLayout + ViewPager
3. Create Fragment classes
4. Set up adapter in Activity

### XML (activity_main.xml)
```xml
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <com.google.android.material.tabs.TabLayout
        android:id="@+id/tabLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:tabMode="fixed"
        app:tabGravity="fill" />

    <androidx.viewpager.widget.ViewPager
        android:id="@+id/viewPager"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
</LinearLayout>
```

### TabFragment.java
```java
public class TabFragment extends Fragment {
    private String content;
    public TabFragment(String content) { this.content = content; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView tv = new TextView(getContext());
        tv.setText(content);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }
}
```

### MainActivity.java
```java
TabLayout tabLayout = findViewById(R.id.tabLayout);
ViewPager viewPager = findViewById(R.id.viewPager);

viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new TabFragment("Artists Content");
            case 1: return new TabFragment("Albums Content");
            default: return new TabFragment("Songs Content");
        }
    }
    @Override public int getCount() { return 3; }
    @Override public CharSequence getPageTitle(int position) {
        return "Tab " + (position + 1);
    }
});
tabLayout.setupWithViewPager(viewPager);
```

---

## 6. TableLayout

### XML
```xml
<TableLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:stretchColumns="1">

    <!-- Header Row -->
    <TableRow>
        <TextView android:text="Name" android:textStyle="bold" android:padding="8dp"/>
        <TextView android:text="Score" android:textStyle="bold" android:padding="8dp"/>
        <TextView android:text="Grade" android:textStyle="bold" android:padding="8dp"/>
    </TableRow>

    <!-- Data Row -->
    <TableRow>
        <TextView android:text="Alice" android:padding="8dp"/>
        <TextView android:text="95" android:padding="8dp"/>
        <TextView android:text="A" android:padding="8dp"/>
    </TableRow>
</TableLayout>
```

### Key Attributes
| Attribute | Description |
|---|---|
| `android:stretchColumns` | Stretches specified column(s) to fill space |
| `android:shrinkColumns` | Shrinks column(s) to fit content |
| `android:collapseColumns` | Hides column(s) without removing |

---

## 7. Input Controls

### XML for all controls
```xml
<!-- SeekBar -->
<SeekBar
    android:id="@+id/seekBar"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:max="100" />

<!-- CheckBox -->
<CheckBox
    android:id="@+id/checkBox"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Option 1" />

<!-- ToggleButton -->
<ToggleButton
    android:id="@+id/toggleButton"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:textOn="ON"
    android:textOff="OFF" />

<!-- RadioGroup + RadioButton -->
<RadioGroup
    android:id="@+id/radioGroup"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content">
    <RadioButton android:id="@+id/rb1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Option A" />
    <RadioButton android:id="@+id/rb2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Option B" />
</RadioGroup>

<!-- Switch -->
<Switch
    android:id="@+id/mySwitch"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Dark Mode" />
```

### MainActivity.java
```java
// SeekBar
SeekBar seekBar = findViewById(R.id.seekBar);
seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
    @Override public void onProgressChanged(SeekBar s, int progress, boolean fromUser) {
        Toast.makeText(MainActivity.this, "Progress: " + progress, Toast.LENGTH_SHORT).show();
    }
    @Override public void onStartTrackingTouch(SeekBar s) {}
    @Override public void onStopTrackingTouch(SeekBar s) {}
});

// CheckBox
CheckBox checkBox = findViewById(R.id.checkBox);
checkBox.setOnCheckedChangeListener((buttonView, isChecked) ->
    Toast.makeText(this, isChecked ? "Checked" : "Unchecked", Toast.LENGTH_SHORT).show());

// ToggleButton
ToggleButton toggle = findViewById(R.id.toggleButton);
toggle.setOnCheckedChangeListener((buttonView, isChecked) ->
    Toast.makeText(this, isChecked ? "ON" : "OFF", Toast.LENGTH_SHORT).show());

// RadioGroup
RadioGroup radioGroup = findViewById(R.id.radioGroup);
radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
    RadioButton rb = findViewById(checkedId);
    Toast.makeText(this, "Selected: " + rb.getText(), Toast.LENGTH_SHORT).show();
});

// Switch
Switch mySwitch = findViewById(R.id.mySwitch);
mySwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
    Toast.makeText(this, isChecked ? "Switch ON" : "Switch OFF", Toast.LENGTH_SHORT).show());
```

---

## 8. Intents

### Explicit Intent (navigate within same app)
```java
// From MainActivity to SecondActivity
Intent intent = new Intent(MainActivity.this, SecondActivity.class);
intent.putExtra("key", "Hello from Main!");   // pass data
startActivity(intent);

// In SecondActivity.java — receive data
String value = getIntent().getStringExtra("key");
```

### Implicit Intent (open external apps)
```java
// Open a URL in browser
Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
startActivity(intent);

// Send an email
Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:someone@example.com"));
emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
emailIntent.putExtra(Intent.EXTRA_TEXT, "Email body");
startActivity(emailIntent);

// Make a phone call
Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:1234567890"));
startActivity(callIntent);
```

---

## 9. Toast

```java
// Short toast
Toast.makeText(this, "Hello!", Toast.LENGTH_SHORT).show();

// Long toast
Toast.makeText(getApplicationContext(), "Operation done!", Toast.LENGTH_LONG).show();

// Inside a click listener
button.setOnClickListener(view ->
    Toast.makeText(MainActivity.this, "Button Clicked!", Toast.LENGTH_SHORT).show());
```

---

## 10. Spinners

### Steps:
1. Add `<Spinner>` in XML
2. Create `ArrayAdapter` with data
3. Set adapter on spinner
4. Implement `OnItemSelectedListener`

### XML
```xml
<Spinner
    android:id="@+id/spinner"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```

### MainActivity.java
```java
Spinner spinner = findViewById(R.id.spinner);
String[] vehicles = {"Car", "Bike", "Bus", "Truck"};

ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
    android.R.layout.simple_spinner_item, vehicles);
adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
spinner.setAdapter(adapter);

spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selected = parent.getItemAtPosition(position).toString();
        Toast.makeText(MainActivity.this, "Selected: " + selected, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) { }
});
```

---

## 11. Pickers

### DatePickerDialog
```java
// Get current date
Calendar calendar = Calendar.getInstance();
int year  = calendar.get(Calendar.YEAR);
int month = calendar.get(Calendar.MONTH);
int day   = calendar.get(Calendar.DAY_OF_MONTH);

Button btnDate = findViewById(R.id.btnDate);
btnDate.setOnClickListener(v -> {
    DatePickerDialog dialog = new DatePickerDialog(this,
        (view, y, m, d) -> {
            String date = d + "/" + (m + 1) + "/" + y;
            Toast.makeText(this, "Date: " + date, Toast.LENGTH_SHORT).show();
        }, year, month, day);
    dialog.show();
});
```

### TimePickerDialog
```java
Calendar cal = Calendar.getInstance();
int hour   = cal.get(Calendar.HOUR_OF_DAY);
int minute = cal.get(Calendar.MINUTE);

Button btnTime = findViewById(R.id.btnTime);
btnTime.setOnClickListener(v -> {
    TimePickerDialog dialog = new TimePickerDialog(this,
        (view, h, min) -> {
            String time = h + ":" + String.format("%02d", min);
            Toast.makeText(this, "Time: " + time, Toast.LENGTH_SHORT).show();
        }, hour, minute, true);  // true = 24hr format
    dialog.show();
});
```

---

## 12. Option Menu & App Bar

### Steps:
1. Create `res/menu/menu_main.xml`
2. Override `onCreateOptionsMenu()` in Activity
3. Handle clicks in `onOptionsItemSelected()`

### res/menu/menu_main.xml
```xml
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:id="@+id/action_search"
        android:title="Search"
        android:icon="@drawable/ic_search"
        android:showAsAction="ifRoom" />
    <item
        android:id="@+id/action_settings"
        android:title="Settings"
        android:showAsAction="never" />
    <item
        android:id="@+id/action_about"
        android:title="About Us"
        android:showAsAction="never" />
</menu>
```

### MainActivity.java
```java
@Override
public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
        case R.id.action_search:
            Toast.makeText(this, "Search clicked", Toast.LENGTH_SHORT).show();
            return true;
        case R.id.action_settings:
            Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show();
            return true;
        case R.id.action_about:
            Toast.makeText(this, "About Us", Toast.LENGTH_SHORT).show();
            return true;
        default:
            return super.onOptionsItemSelected(item);
    }
}
```

### App Bar (Toolbar) — XML
```xml
<androidx.appcompat.widget.Toolbar
    android:id="@+id/toolbar"
    android:layout_width="match_parent"
    android:layout_height="?attr/actionBarSize"
    android:background="?attr/colorPrimary" />
```

### App Bar — MainActivity.java
```java
Toolbar toolbar = findViewById(R.id.toolbar);
setSupportActionBar(toolbar);
```

---

## 13. Context Menu (Floating)

### Steps:
1. Register the view: `registerForContextMenu(view)`
2. Create `res/menu/context_menu.xml`
3. Override `onCreateContextMenu()` and `onContextItemSelected()`

### res/menu/context_menu.xml
```xml
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:id="@+id/action_edit"   android:title="Edit" />
    <item android:id="@+id/action_delete" android:title="Delete" />
    <item android:id="@+id/action_share"  android:title="Share" />
</menu>
```

### MainActivity.java
```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    View myView = findViewById(R.id.my_view);
    registerForContextMenu(myView);   // Step 1: register
}

// Step 2: inflate menu
@Override
public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
    super.onCreateContextMenu(menu, v, menuInfo);
    getMenuInflater().inflate(R.menu.context_menu, menu);
    menu.setHeaderTitle("Choose Action");
}

// Step 3: handle clicks
@Override
public boolean onContextItemSelected(MenuItem item) {
    switch (item.getItemId()) {
        case R.id.action_edit:
            Toast.makeText(this, "Edit selected", Toast.LENGTH_SHORT).show();
            return true;
        case R.id.action_delete:
            Toast.makeText(this, "Delete selected", Toast.LENGTH_SHORT).show();
            return true;
        default:
            return super.onContextItemSelected(item);
    }
}
```

---

## 14. Contextual Action Mode

### Steps:
1. Set `listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL)`
2. Set `MultiChoiceModeListener`
3. Create `res/menu/contextual_action_menu.xml`

### res/menu/contextual_action_menu.xml
```xml
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:id="@+id/action_delete"
        android:title="Delete"
        android:showAsAction="ifRoom" />
</menu>
```

### MainActivity.java
```java
ListView listView = findViewById(R.id.my_list);
listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.contextual_action_menu, menu);
        return true;
    }
    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int pos, long id, boolean checked) {
        mode.setTitle(listView.getCheckedItemCount() + " selected");
    }
    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) { return false; }
    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            Toast.makeText(MainActivity.this, "Deleted!", Toast.LENGTH_SHORT).show();
            mode.finish();
            return true;
        }
        return false;
    }
    @Override
    public void onDestroyActionMode(ActionMode mode) { }
});
```

---

## 15. Popup Menu

### Steps:
1. Create `res/menu/popup_menu.xml`
2. On button click, create `PopupMenu(context, anchorView)`
3. Inflate with `MenuInflater`
4. Set `OnMenuItemClickListener`
5. Call `.show()`

### res/menu/popup_menu.xml
```xml
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:id="@+id/option_one"   android:title="Option One" />
    <item android:id="@+id/option_two"   android:title="Option Two" />
    <item android:id="@+id/option_three" android:title="Option Three" />
</menu>
```

### XML Button
```xml
<Button
    android:id="@+id/popupButton"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Show Popup Menu" />
```

### MainActivity.java
```java
Button popupButton = findViewById(R.id.popupButton);
popupButton.setOnClickListener(view -> {
    PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);   // Step 2
    popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu()); // Step 3

    popupMenu.setOnMenuItemClickListener(item -> {                  // Step 4
        switch (item.getItemId()) {
            case R.id.option_one:
                Toast.makeText(this, "Option One", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.option_two:
                Toast.makeText(this, "Option Two", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.option_three:
                Toast.makeText(this, "Option Three", Toast.LENGTH_SHORT).show();
                return true;
            default: return false;
        }
    });
    popupMenu.show();   // Step 5
});
```

---

## 16. SQLite Database

### Steps:
1. Create a helper class extending `SQLiteOpenHelper`
2. Override `onCreate()` and `onUpgrade()`
3. Use `getWritableDatabase()` / `getReadableDatabase()` to get DB instance
4. Use `insert()`, `query()`, `update()`, `delete()` methods
5. Always close `Cursor` and `db` after use

### MyDatabaseHelper.java
```java
public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyDatabase.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Users";

    private static final String CREATE_TABLE =
        "CREATE TABLE " + TABLE_NAME + " (" +
        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "name TEXT NOT NULL, " +
        "email TEXT UNIQUE);";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
```

### INSERT
```java
MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
SQLiteDatabase db = dbHelper.getWritableDatabase();

ContentValues values = new ContentValues();
values.put("name", "John Doe");
values.put("email", "john@example.com");

long newRowId = db.insert("Users", null, values);
Toast.makeText(this, "Inserted row: " + newRowId, Toast.LENGTH_SHORT).show();
db.close();
```

### READ / QUERY
```java
SQLiteDatabase db = dbHelper.getReadableDatabase();
Cursor cursor = db.query(
    "Users",                               // table
    new String[]{"id", "name", "email"},   // columns
    null, null, null, null, null           // no WHERE clause = get all
);

while (cursor.moveToNext()) {
    int id       = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
    String name  = cursor.getString(cursor.getColumnIndexOrThrow("name"));
    String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
    Log.d("DB", id + " | " + name + " | " + email);
}
cursor.close();
db.close();
```

### READ with WHERE clause
```java
Cursor cursor = db.query("Users", null,
    "email = ?", new String[]{"john@example.com"},
    null, null, null);
```

### UPDATE
```java
SQLiteDatabase db = dbHelper.getWritableDatabase();
ContentValues values = new ContentValues();
values.put("name", "John Smith");

int rowsAffected = db.update("Users", values,
    "email = ?", new String[]{"john@example.com"});
db.close();
```

### DELETE
```java
SQLiteDatabase db = dbHelper.getWritableDatabase();
int rowsDeleted = db.delete("Users",
    "email = ?", new String[]{"john@example.com"});
db.close();
```

### Raw SQL alternatives
```java
// Raw insert
db.execSQL("INSERT INTO Users (name, email) VALUES ('Jane', 'jane@example.com');");

// Raw query
Cursor cursor = db.rawQuery("SELECT * FROM Users", null);

// Raw update
db.execSQL("UPDATE Users SET name='New Name' WHERE email='john@example.com';");

// Raw delete
db.execSQL("DELETE FROM Users WHERE email='john@example.com';");
```

### View DB in Android Studio
```
Tools → Device File Explorer →
/data/data/<your.package.name>/databases/
→ Right-click → Save As → open with DB Browser for SQLite
```

---

## 17. Shared Preferences

### What is it?
Key-value pairs stored persistently as XML in `/data/data/<pkg>/shared_prefs/`.
Used for: user settings, login state, config flags, small cached data.

### Steps:
1. Get `SharedPreferences` object
2. Use `Editor` to write
3. Call `apply()` or `commit()` to save
4. Use `get*()` methods to read

### Initialize
```java
SharedPreferences prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
```

### SAVE data
```java
SharedPreferences.Editor editor = prefs.edit();
editor.putString("username", "JohnDoe");
editor.putBoolean("isLoggedIn", true);
editor.putInt("age", 25);
editor.putFloat("score", 98.5f);
editor.apply();   // async (preferred) — or use .commit() for sync
```

### READ data
```java
String username  = prefs.getString("username", "DefaultUser");  // 2nd arg = default
boolean loggedIn = prefs.getBoolean("isLoggedIn", false);
int age          = prefs.getInt("age", 0);
float score      = prefs.getFloat("score", 0.0f);
```

### REMOVE a key
```java
SharedPreferences.Editor editor = prefs.edit();
editor.remove("username");
editor.apply();
```

### CLEAR all data
```java
SharedPreferences.Editor editor = prefs.edit();
editor.clear();
editor.apply();
```

### CHECK if key exists
```java
if (prefs.contains("username")) {
    // key exists
}
```

### Practical example — Save on close, load on open
```java
// In onPause() or onStop() — SAVE
@Override
protected void onPause() {
    super.onPause();
    SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
    editor.putString("savedText", editText.getText().toString());
    editor.apply();
}

// In onCreate() — LOAD
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
    String saved = prefs.getString("savedText", "");
    editText.setText(saved);
}
```

---

## 🔑 Quick Reference — Common Imports

```java
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.PopupMenu;
import android.view.ActionMode;
import android.net.Uri;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
```

---

## 🗂️ File/Folder Cheat Sheet

| What | Where |
|---|---|
| Layouts | `res/layout/activity_main.xml` |
| Menu XML | `res/menu/menu_name.xml` |
| Drawables | `res/drawable/` |
| Strings | `res/values/strings.xml` |
| Java code | `java/com.example.app/MainActivity.java` |
| Manifest | `manifests/AndroidManifest.xml` |
| DB location | `/data/data/<pkg>/databases/` |
| SharedPrefs | `/data/data/<pkg>/shared_prefs/` |

---

*Good luck in your lab exam! 🎯*