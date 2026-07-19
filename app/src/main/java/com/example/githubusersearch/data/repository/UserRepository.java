package com.example.githubusersearch.data.repository;

import com.example.githubusersearch.data.api.GithubApi;
import com.example.githubusersearch.data.model.User;
import com.example.githubusersearch.data.network.RetrofitInstance;

import retrofit2.Call;

public class UserRepository {

    private final GithubApi api;

    public UserRepository() {
        api = RetrofitInstance.getApi();
    }

    public Call<User> getUser(String username) {
        return api.getUser(username);
    }
}
