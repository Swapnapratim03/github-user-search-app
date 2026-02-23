package com.example.githubusersearch.viewmodel

import android.content.Context
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubusersearch.data.model.User
import com.example.githubusersearch.data.repository.SearchHistoryManager
import com.example.githubusersearch.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val repository = UserRepository()

    var user by mutableStateOf<User?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun fetchUser(username: String, context: Context) {

        val historyManager = SearchHistoryManager(context)

        viewModelScope.launch {

            isLoading = true

            try {
                val result = repository.getUser(username)
                user = result

                //  save valid user
                historyManager.saveSearch(username)

            } catch (e: Exception) {
                user = null
            }

            isLoading = false
        }
    }
}