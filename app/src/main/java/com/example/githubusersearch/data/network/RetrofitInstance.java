package com.example.githubusersearch.data.network;

import com.example.githubusersearch.data.api.GithubApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static volatile GithubApi api;

    public static GithubApi getApi() {
        if (api == null) {
            synchronized (RetrofitInstance.class) {
                if (api == null) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://api.github.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    api = retrofit.create(GithubApi.class);
                }
            }
        }
        return api;
    }
}
