package com.example.githubusersearch.data.api;

import com.example.githubusersearch.data.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubApi {
    @GET("users/{username}")
    Call<User> getUser(@Path("username") String username);
}
