package com.example.github.Interfaces

import com.example.github.Model.DetailUserData
import com.example.github.Model.FollowData
import com.example.github.Model.SearchResponse
import com.example.github.Model.UserData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET("users")
    @Headers("Authorization: token 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")
    fun getListUsers(): Call<List<UserData>>

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") query: String
    ) : Call<SearchResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserData(
        @Path("username") username: String
    ): Call<DetailUserData>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<FollowData>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<FollowData>>
}