package com.example.githubuser.api

import com.example.githubuser.model.DetailUserResponse
import com.example.githubuser.model.GithubResponse
import com.example.githubuser.model.ItemsItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getItem(@Query("q")query: String): Call<GithubResponse>
    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(@Path("username") username: String): Call<ArrayList<ItemsItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(@Path("username") username: String): Call<ArrayList<ItemsItem>>

}