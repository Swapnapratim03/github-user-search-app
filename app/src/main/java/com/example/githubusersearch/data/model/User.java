package com.example.githubusersearch.data.model;

import com.google.gson.annotations.SerializedName;

public class User {
    private String login;
    private String name;

    @SerializedName("avatar_url")
    private String avatarUrl;

    private String bio;
    private int followers;
    private int following;

    @SerializedName("public_repos")
    private int publicRepos;

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getBio() {
        return bio;
    }

    public int getFollowers() {
        return followers;
    }

    public int getFollowing() {
        return following;
    }

    public int getPublicRepos() {
        return publicRepos;
    }
}
