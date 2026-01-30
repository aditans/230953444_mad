package com.example.lifecycle;
import com.google.firebase.Timestamp;
import android.os.Bundle;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.util.Log;
public class MainActivity extends AppCompatActivity {
    TextView lifecycleText;
    String TAG = "LifecycleDemo";

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
        lifecycleText = findViewById(R.id.textView);

        Log.d(TAG, "onCreate called");
        Timestamp now = Timestamp.now();

        lifecycleText.append("onCreate() called at :"+now.toDate() +"\n");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart called");
        Timestamp now = Timestamp.now();

        lifecycleText.append("onStart() called at :"+now.toDate()+"\n");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume called");
        Timestamp now = Timestamp.now();

        lifecycleText.append("onResume() called at :"+now.toDate()+"\n");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop called");
        Timestamp now = Timestamp.now();

        lifecycleText.append("onStop() called at :"+now.toDate()+"\n");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart called");
        Timestamp now = Timestamp.now();

        lifecycleText.append("onRestart() called at :"+now.toDate()+"\n");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy called");
        Timestamp now = Timestamp.now();

        lifecycleText.append("onDestroy() called at :"+now.toDate()+"\n");
    }
}