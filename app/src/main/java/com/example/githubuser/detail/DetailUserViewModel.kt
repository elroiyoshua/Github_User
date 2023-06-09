package com.example.githubuser.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubuser.api.ApiConfig
import com.example.githubuser.datalocal.FavoriteUser
import com.example.githubuser.datalocal.FavoriteUserDAO
import com.example.githubuser.datalocal.UserDatabase
import com.example.githubuser.model.DetailUserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<DetailUserResponse>()
    private var userDao: FavoriteUserDAO?
    private var userDb: UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun setUserDetail(username: String) {
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>, response: Response<DetailUserResponse>
            ) {
                if (response.isSuccessful) {
                    user.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                Log.d("GAGAL", t.message.toString())
            }

        })
    }

    fun getUserDetail(): LiveData<DetailUserResponse> {
        return user
    }

    fun addToFavorite(username: String, id: Int,avatarUrl : String) {
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavoriteUser(username, id, avatarUrl)
            userDao?.addToFavorite(user)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkUser(id)
    fun removeFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }
}