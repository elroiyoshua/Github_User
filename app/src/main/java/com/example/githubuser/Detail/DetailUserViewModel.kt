package com.example.githubuser.Detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.Api.ApiConfig
import com.example.githubuser.Model.DetailUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel:ViewModel() {
    val user = MutableLiveData<DetailUserResponse>()

    fun setUserDetail(username : String){
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object :Callback<DetailUserResponse>{
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if(response.isSuccessful){
                    user.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                Log.d("GAGAL",t.message.toString())
            }

        })
    }
    fun getUserDetail():LiveData<DetailUserResponse>{
        return user
    }
}