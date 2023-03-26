package com.example.githubuser

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getItem(@Query("q")query: String): Call<GithubResponse>
    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>
}