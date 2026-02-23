package com.example.githubusersearch.data.repository

import com.example.githubusersearch.data.model.User
import com.example.githubusersearch.data.network.RetrofitInstance

class UserRepository {

    suspend fun getUser(username: String): User {
        return RetrofitInstance.api.getUser(username)
    }
}