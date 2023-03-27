package com.example.githubuser.Main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.Api.ApiConfig
import com.example.githubuser.Model.GithubResponse
import com.example.githubuser.Model.ItemsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel(){
    private val _Items  = MutableLiveData<ArrayList<ItemsItem>>()
    val ItemsItem  : LiveData<ArrayList<ItemsItem>> = _Items
    init {

    }

    fun setSearch(query : String){
        val client = ApiConfig.getApiService().getItem(query)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                if (response.isSuccessful){
                    if (response.body() != null){
                        _Items.value = response.body()?.items as ArrayList<ItemsItem>?
                    }
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                Log.d("failure",t.message.toString())
            }

        })
    }
    fun getSearchUssers() : LiveData<ArrayList<ItemsItem>>{
        return _Items
    }
}