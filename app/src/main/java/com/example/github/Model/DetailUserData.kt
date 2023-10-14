package com.example.github.Model

data class DetailUserData(
    val login: String,
    val avatar_url: String,
    val name: String,
    val company: String,
    val location: String,
    val public_repos: Int,
    val followers: Int,
    val following: Int

)