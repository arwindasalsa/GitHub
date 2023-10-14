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