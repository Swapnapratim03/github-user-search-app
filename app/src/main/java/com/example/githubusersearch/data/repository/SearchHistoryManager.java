package com.example.githubusersearch.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchHistoryManager {

    private final SharedPreferences prefs;

    public SearchHistoryManager(Context context) {
        prefs = context.getSharedPreferences("search_history", Context.MODE_PRIVATE);
    }

    public List<String> getHistory() {
        Set<String> set = prefs.getStringSet("history", new HashSet<>());
        if (set == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(set);
    }

    public void saveSearch(String username) {
        List<String> history = getHistory();
        history.remove(username);
        history.add(0, username);

        List<String> limited = history.subList(0, Math.min(history.size(), 5));
        Set<String> set = new HashSet<>(limited);
        prefs.edit().putStringSet("history", set).apply();
    }
}
