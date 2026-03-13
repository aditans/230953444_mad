package com.example.taskmgr;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
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

    private ListView appListView;
    private List<ApplicationInfo> installedApps;
    private List<String> appNames;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appListView = findViewById(R.id.appListView);
        registerForContextMenu(appListView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadInstalledApps();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, appNames);
        appListView.setAdapter(adapter);
    }

    private void loadInstalledApps() {
        PackageManager pm = getPackageManager();
        // GET_META_DATA is standard for listing apps
        installedApps = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        appNames = new ArrayList<>();
        for (ApplicationInfo app : installedApps) {
            appNames.add(pm.getApplicationLabel(app).toString());
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_context_menu, menu);
        menu.setHeaderTitle("Select Action");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (info == null) return false;
        
        ApplicationInfo selectedApp = installedApps.get(info.position);

        int itemId = item.getItemId();
        if (itemId == R.id.action_app_type) {
            boolean isSystemApp = (selectedApp.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
            String type = isSystemApp ? "System App" : "User Installed App";
            Toast.makeText(this, "Type: " + type, Toast.LENGTH_LONG).show();
            return true;
        } else if (itemId == R.id.action_open) {
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage(selectedApp.packageName);
            if (launchIntent != null) {
                startActivity(launchIntent);
            } else {
                Toast.makeText(this, "Cannot open this app", Toast.LENGTH_SHORT).show();
            }
            return true;
        } else if (itemId == R.id.action_uninstall) {
            showUninstallConfirmation(selectedApp);
            return true;
        } else if (itemId == R.id.action_details) {
            navigateToDetails(selectedApp.packageName);
            return true;
        } else if (itemId == R.id.action_permissions) {
            checkSpecialPermissions(selectedApp.packageName);
            return true;
        }
        return super.onContextItemSelected(item);
    }

    private void checkSpecialPermissions(String packageName) {
        try {
            PackageManager pm = getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
            boolean hasLoc = false, hasCam = false;
            if (packageInfo.requestedPermissions != null) {
                for (String p : packageInfo.requestedPermissions) {
                    if (p.contains("LOCATION")) hasLoc = true;
                    if (p.contains("CAMERA")) hasCam = true;
                }
            }
            String msg = "Location: " + (hasLoc ? "Yes" : "No") + "\nCamera: " + (hasCam ? "Yes" : "No");
            new AlertDialog.Builder(this).setTitle("Permissions").setMessage(msg).setPositiveButton("OK", null).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error fetching permissions", Toast.LENGTH_SHORT).show();
        }
    }

    private void showUninstallConfirmation(ApplicationInfo app) {
        if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
            Toast.makeText(this, "System apps cannot be uninstalled", Toast.LENGTH_SHORT).show();
            return;
        }
        new AlertDialog.Builder(this)
                .setTitle("Uninstall App")
                .setMessage("Are you sure you want to uninstall " + getPackageManager().getApplicationLabel(app) + "?")
                .setPositiveButton("Uninstall", (dialog, which) -> {
                    Intent intent = new Intent(Intent.ACTION_DELETE);
                    intent.setData(Uri.parse("package:" + app.packageName));
                    startActivity(intent);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void navigateToDetails(String packageName) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("PACKAGE_NAME", packageName);
        startActivity(intent);
    }
}
