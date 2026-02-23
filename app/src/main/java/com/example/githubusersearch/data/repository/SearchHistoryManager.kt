package com.example.githubusersearch.data.repository

import android.content.Context

class SearchHistoryManager(context: Context) {

    private val prefs = context.getSharedPreferences("search_history", Context.MODE_PRIVATE)

    fun saveSearch(username: String) {
        val history = getHistory().toMutableList()

        history.remove(username) // remove duplicate
        history.add(0, username) // add to top

        val limited = history.take(5)

        prefs.edit().putStringSet("history", limited.toSet()).apply()
    }

    fun getHistory(): List<String> {
        return prefs.getStringSet("history", emptySet())?.toList() ?: emptyList()
    }
}