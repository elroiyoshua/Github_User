package com.example.githubuser

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("users")
    fun getItem(): Call<List<ItemsItem>>
}