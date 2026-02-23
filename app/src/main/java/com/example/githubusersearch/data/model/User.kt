
package com.example.githubusersearch.data.model

data class User(
    val name: String?,
    val bio: String?,
    val avatar_url: String,

    val login: String,
    val followers: Int,
    val following: Int,
    val public_repos: Int
)