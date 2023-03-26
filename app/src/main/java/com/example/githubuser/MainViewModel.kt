package com.example.githubuser

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel(){
    private val _Items  = MutableLiveData<ArrayList<ItemsItem>>()
    val ItemsItem  : LiveData<ArrayList<ItemsItem>> = _Items

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    init {

    }

    fun setSearch(query : String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getItem(query)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {_isLoading.value = false
                if (response.isSuccessful){
                    if (response.body() != null){
                        _Items.value = response.body()?.items as ArrayList<ItemsItem>?
                    }
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.d("failure",t.message.toString())
            }

        })
    }
    fun getSearchUssers() : LiveData<ArrayList<ItemsItem>>{
        return _Items
    }
}