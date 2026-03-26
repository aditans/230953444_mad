package com.example.pop_up_menu;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Global variables for widgets
    private ListView appListView;
    private List<ApplicationInfo> installedApps;
    private List<String> appNames;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Initialize ListView
        setupAppListView();
    }

    // --- ListView Setup Logic ---
    private void setupAppListView() {
        appListView = findViewById(R.id.appListView);
        
        // Fetch installed apps using PackageManager
        PackageManager pm = getPackageManager();
        installedApps = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        appNames = new ArrayList<>();

        for (ApplicationInfo app : installedApps) {
            appNames.add(pm.getApplicationLabel(app).toString());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, appNames);
        appListView.setAdapter(adapter);

        // Register for Context Menu
        registerForContextMenu(appListView);
    }

    // --- Context Menu Logic ---
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.app_context_menu, menu);
        
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(appNames.get(info.position));
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        ApplicationInfo selectedApp = installedApps.get(info.position);
        String packageName = selectedApp.packageName;

        int itemId = item.getItemId();
        if (itemId == R.id.action_app_type) {
            showAppType(selectedApp);
            return true;
        } else if (itemId == R.id.action_open) {
            openApp(packageName);
            return true;
        } else if (itemId == R.id.action_view_details) {
            viewAppDetails(selectedApp);
            return true;
        } else if (itemId == R.id.action_permissions) {
            checkPermissions(packageName);
            return true;
        } else if (itemId == R.id.action_uninstall) {
            confirmUninstall(packageName);
            return true;
        }
        return super.onContextItemSelected(item);
    }

    // --- Helper Logic Blocks ---

    // App Type Logic
    private void showAppType(ApplicationInfo app) {
        String type = (app.flags & ApplicationInfo.FLAG_SYSTEM) != 0 ? "System App" : "User Installed";
        Toast.makeText(this, "Type: " + type, Toast.LENGTH_SHORT).show();
    }

    // Open App Logic
    private void openApp(String packageName) {
        Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "Cannot open this app", Toast.LENGTH_SHORT).show();
        }
    }

    // View Details Logic (Simplified for Exam)
    private void viewAppDetails(ApplicationInfo app) {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(app.packageName, 0);
            String details = "Version: " + pInfo.versionName + "\n" +
                             "Package: " + app.packageName + "\n" +
                             "Target SDK: " + app.targetSdkVersion;
            
            new AlertDialog.Builder(this)
                    .setTitle("App Details")
                    .setMessage(details)
                    .setPositiveButton("OK", null)
                    .show();
        } catch (Exception e) {
            Toast.makeText(this, "Error fetching details", Toast.LENGTH_SHORT).show();
        }
    }

    // Permissions Logic
    private void checkPermissions(String packageName) {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
            String[] permissions = pInfo.requestedPermissions;
            if (permissions == null) {
                Toast.makeText(this, "No special permissions requested", Toast.LENGTH_SHORT).show();
                return;
            }
            
            StringBuilder sb = new StringBuilder("Requested Permissions:\n");
            for (String p : permissions) {
                if (p.contains("LOCATION") || p.contains("CAMERA")) {
                    sb.append("- ").append(p.substring(p.lastIndexOf(".") + 1)).append("\n");
                }
            }
            Toast.makeText(this, sb.length() > 22 ? sb.toString() : "No Location/Camera permissions", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error checking permissions", Toast.LENGTH_SHORT).show();
        }
    }

    // Uninstall Logic with Confirmation
    private void confirmUninstall(String packageName) {
        new AlertDialog.Builder(this)
                .setTitle("Uninstall App")
                .setMessage("Are you sure you want to uninstall " + packageName + "?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    Intent intent = new Intent(Intent.ACTION_DELETE);
                    intent.setData(Uri.parse("package:" + packageName));
                    startActivity(intent);
                })
                .setNegativeButton("No", null)
                .show();
    }
}