package com.example.githubusersearch.data.api

import com.example.githubusersearch.data.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {

    @GET("users/{username}")
    suspend fun getUser(
        @Path("username") username: String
    ): User
}