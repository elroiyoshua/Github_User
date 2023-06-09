package com.example.githubuser.api

import com.example.githubuser.model.DetailUserResponse
import com.example.githubuser.model.GithubResponse
import com.example.githubuser.model.ItemsItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token //insertyourtokenhere")
    fun getItem(@Query("q")query: String): Call<GithubResponse>
    @GET("users/{username}")
    @Headers("Authorization: token //insertyourtokenhere")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token //insertyourtokenhere")
    fun getFollowers(@Path("username") username: String): Call<ArrayList<ItemsItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token //insertyourtokenhere")
    fun getFollowing(@Path("username") username: String): Call<ArrayList<ItemsItem>>

}