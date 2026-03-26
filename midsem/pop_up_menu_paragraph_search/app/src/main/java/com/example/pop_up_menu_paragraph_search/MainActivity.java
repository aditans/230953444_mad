package com.example.pop_up_menu_paragraph_search;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView contentTextView;
    private EditText inputField;
    private String originalContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Toolbar Setup (Crucial for 3-dots menu visibility in NoActionBar themes)
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 2. Initialization
        contentTextView = findViewById(R.id.contentText);
        inputField = findViewById(R.id.inputField);
        originalContent = contentTextView.getText().toString();
    }

    // --- OPTIONS MENU SETUP ---
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.popup_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String query = inputField.getText().toString().trim();

        int itemId = item.getItemId();
        if (itemId == R.id.menu_search) {
            handleSearch(query);
            return true;
        } else if (itemId == R.id.menu_highlight) {
            handleHighlight(query);
            return true;
        } else if (itemId == R.id.sort_alphabetical) {
            handleSort(true, query);
            return true;
        } else if (itemId == R.id.sort_relevance) {
            handleSort(false, query);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // --- SEARCH LOGIC ---
    private void handleSearch(String query) {
        if (query.isEmpty()) {
            Toast.makeText(this, "Please enter a keyword to search", Toast.LENGTH_SHORT).show();
            return;
        }
        if (originalContent.toLowerCase().contains(query.toLowerCase())) {
            Toast.makeText(this, "Keyword found!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Keyword not found.", Toast.LENGTH_SHORT).show();
        }
    }

    // --- HIGHLIGHT LOGIC ---
    private void handleHighlight(String query) {
        if (query.isEmpty()) {
            contentTextView.setText(originalContent);
            return;
        }
        SpannableString spannable = new SpannableString(originalContent);
        String lowerContent = originalContent.toLowerCase();
        String lowerQuery = query.toLowerCase();

        int index = lowerContent.indexOf(lowerQuery);
        while (index >= 0) {
            spannable.setSpan(new BackgroundColorSpan(Color.YELLOW),
                    index, index + query.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            index = lowerContent.indexOf(lowerQuery, index + query.length());
        }
        contentTextView.setText(spannable);
    }

    // --- SORT LOGIC ---
    private void handleSort(boolean alphabetical, String query) {
        // Split content into individual words for more visible sorting results
        String[] words = originalContent.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
        List<String> wordList = new ArrayList<>(Arrays.asList(words));

        if (alphabetical) {
            // Sort words alphabetically (A-Z)
            Collections.sort(wordList);
        } else {
            // Sort by relevance (count of occurrences of query)
            if (query.isEmpty()) {
                Toast.makeText(this, "Enter keyword for relevance sort", Toast.LENGTH_SHORT).show();
                return;
            }
            final String finalQuery = query.toLowerCase();
            Collections.sort(wordList, new Comparator<String>() {
                @Override
                public int compare(String s1, String s2) {
                    // Relevance here is defined as: words matching the query come first
                    boolean match1 = s1.equals(finalQuery);
                    boolean match2 = s2.equals(finalQuery);
                    if (match1 && !match2) return -1;
                    if (!match1 && match2) return 1;
                    return s1.compareTo(s2); // If both match or both don't, sort alphabetically
                }
            });
        }

        StringBuilder sb = new StringBuilder();
        for (String word : wordList) {
            sb.append(word).append(" ");
        }
        contentTextView.setText(sb.toString().trim());
    }

    private int countOccurrences(String text, String query) {
        if (query.isEmpty()) return 0;
        int count = 0;
        int index = text.indexOf(query);
        while (index >= 0) {
            count++;
            index = text.indexOf(query, index + query.length());
        }
        return count;
    }
}