package com.example.github.Model

data class UserData(
    val login : String,
    var id : Int = 0,
    val avatar_url : String,
    val followers: Int,
    val folowing: String,
    var name: String
)