package com.example.digitaltransformation;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvDescription;
    private String originalText;
    private String currentSearchKeyword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDescription = findViewById(R.id.tvDescription);
        originalText = tvDescription.getText().toString();
        Button btnFilter = findViewById(R.id.btnFilter);

        btnFilter.setOnClickListener(v -> showFilterMenu());
    }

    private void showFilterMenu() {
        PopupMenu popup = new PopupMenu(this, findViewById(R.id.btnFilter));
        popup.getMenuInflater().inflate(R.menu.filter_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.action_search) {
                showInputDialog("Search Keywords", (input) -> {
                    currentSearchKeyword = input;
                    searchKeyword(input);
                });
                return true;
            } else if (itemId == R.id.action_highlight) {
                showInputDialog("Highlight", this::highlightText);
                return true;
            } else if (itemId == R.id.sort_alphabetical) {
                sortContent(true);
                return true;
            } else if (itemId == R.id.sort_relevance) {
                sortContent(false);
                return true;
            }
            return false;
        });
        popup.show();
    }

    private void showInputDialog(String title, InputCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        final EditText input = new EditText(this);
        input.setHint("Enter text...");
        builder.setView(input);
        builder.setPositiveButton("OK", (dialog, which) -> callback.onInput(input.getText().toString()));
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void searchKeyword(String keyword) {
        if (keyword.isEmpty()) {
            tvDescription.setText(originalText);
            return;
        }
        int count = countOccurrences(originalText, keyword);
        Toast.makeText(this, "Found " + count + " occurrences of '" + keyword + "'", Toast.LENGTH_SHORT).show();
    }

    private void highlightText(String textToHighlight) {
        if (textToHighlight.isEmpty()) {
            tvDescription.setText(originalText);
            return;
        }
        SpannableString spannableString = new SpannableString(originalText);
        String lowerText = originalText.toLowerCase();
        String lowerHighlight = textToHighlight.toLowerCase();
        int index = lowerText.indexOf(lowerHighlight);
        while (index >= 0) {
            spannableString.setSpan(new BackgroundColorSpan(Color.YELLOW), index, index + textToHighlight.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            index = lowerText.indexOf(lowerHighlight, index + textToHighlight.length());
        }
        tvDescription.setText(spannableString);
    }

    private void sortContent(boolean alphabetical) {
        String text = tvDescription.getText().toString();
        // Split by sentence endings followed by whitespace
        String[] sentences = text.split("(?<=[.!?])\\s+");
        List<String> sentenceList = new ArrayList<>(Arrays.asList(sentences));

        if (alphabetical) {
            sentenceList.sort(String.CASE_INSENSITIVE_ORDER);
        } else {
            if (currentSearchKeyword.isEmpty()) {
                Toast.makeText(this, "Please search for a keyword first to sort by relevance.", Toast.LENGTH_SHORT).show();
                return;
            }
            sentenceList.sort((s1, s2) -> {
                int count1 = countOccurrences(s1, currentSearchKeyword);
                int count2 = countOccurrences(s2, currentSearchKeyword);
                return Integer.compare(count2, count1); // Descending order
            });
        }

        StringBuilder sb = new StringBuilder();
        for (String sentence : sentenceList) {
            sb.append(sentence).append(" ");
        }
        tvDescription.setText(sb.toString().trim());
    }

    private int countOccurrences(String text, String keyword) {
        int count = 0;
        int index = text.toLowerCase().indexOf(keyword.toLowerCase());
        while (index != -1) {
            count++;
            index = text.toLowerCase().indexOf(keyword.toLowerCase(), index + 1);
        }
        return count;
    }

    interface InputCallback {
        void onInput(String input);
    }
}