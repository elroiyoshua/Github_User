package com.example.githubuser

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {
    val listFollowing = MutableLiveData<ArrayList<ItemsItem>>()

    fun setListFollowing(username: String) {
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<ArrayList<ItemsItem>> {
            override fun onResponse(
                call: Call<ArrayList<ItemsItem>>,
                response: Response<ArrayList<ItemsItem>>
            ) {
                if (response.isSuccessful) {
                    listFollowing.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<ItemsItem>>, t: Throwable) {
                Log.d("Gagal", t.message.toString())
            }

        })
    }

    fun getListFollowing(): LiveData<ArrayList<ItemsItem>> {
        return listFollowing
    }
}