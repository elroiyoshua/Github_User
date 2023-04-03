package com.example.githubuser.Favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubuser.datalocal.FavoriteUser
import com.example.githubuser.datalocal.FavoriteUserDAO
import com.example.githubuser.datalocal.UserDatabase
import com.example.githubuser.model.DetailUserResponse

class FavoriteViewModel(application: Application):AndroidViewModel(application) {

    private var userDao: FavoriteUserDAO?
    private var userDb: UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }
    fun getFavoriteUser(): LiveData<List<FavoriteUser>>?{
        return userDao?.getFavoriteUser()
    }
}