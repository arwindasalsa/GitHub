package com.example.github.Model

data class FollowData (
    val login: String,
    var id : Int = 0,
    val avatar_url: String,
    var followers: Int
)