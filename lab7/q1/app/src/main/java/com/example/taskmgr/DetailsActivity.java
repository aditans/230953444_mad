package com.example.taskmgr;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        String packageName = getIntent().getStringExtra("PACKAGE_NAME");
        if (packageName == null) {
            finish();
            return;
        }

        TextView appNameTv = findViewById(R.id.appName);
        TextView appVersionTv = findViewById(R.id.appVersion);
        TextView appSizeTv = findViewById(R.id.appSize);
        TextView appPermissionsTv = findViewById(R.id.appPermissions);

        try {
            PackageManager pm = getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);

            appNameTv.setText(pm.getApplicationLabel(packageInfo.applicationInfo));
            appVersionTv.setText("Version: " + packageInfo.versionName);

            File file = new File(packageInfo.applicationInfo.sourceDir);
            long sizeInBytes = file.length();
            appSizeTv.setText("Size: " + (sizeInBytes / 1024 / 1024) + " MB");

            StringBuilder permissionsText = new StringBuilder();
            boolean hasLocation = false;
            boolean hasCamera = false;

            if (packageInfo.requestedPermissions != null) {
                for (int i = 0; i < packageInfo.requestedPermissions.length; i++) {
                    String permission = packageInfo.requestedPermissions[i];
                    permissionsText.append(permission).append("\n");
                    
                    if (permission.contains("LOCATION")) hasLocation = true;
                    if (permission.contains("CAMERA")) hasCamera = true;
                }
            } else {
                permissionsText.append("No permissions requested.");
            }
            
            String special = "\nSpecial Permissions:\n";
            special += "Location: " + (hasLocation ? "Requested" : "None") + "\n";
            special += "Camera: " + (hasCamera ? "Requested" : "None") + "\n\n";
            
            appPermissionsTv.setText(special + "All Requested Permissions:\n" + permissionsText.toString());

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
