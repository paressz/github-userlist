package com.farez.githubuser.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.farez.githubuser.data.local.FavoriteUser
import com.farez.githubuser.data.local.FavoriteUserDAO
import com.farez.githubuser.data.local.UserDB

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private var userDAO : FavoriteUserDAO?
    private var userDB : UserDB?
    init {
        userDB = UserDB.getDataBase(application)
        userDAO = userDB?.favUserDAO()
    }

    fun getFav(): LiveData<List<FavoriteUser>>? {
        return userDAO?.getFav()
    }

}