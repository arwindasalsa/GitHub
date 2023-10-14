package com.example.github.Interfaces

import retrofit2.Retrofit

object GithubAPI {

    var retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
        .build()

    var service: GithubService = retrofit.create(GithubService::class.java)

}