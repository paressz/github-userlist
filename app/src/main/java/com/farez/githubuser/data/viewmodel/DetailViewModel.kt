package com.farez.githubuser.data.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.farez.githubuser.api.RetrofitClient
import com.farez.githubuser.data.local.FavoriteUser
import com.farez.githubuser.data.local.FavoriteUserDAO
import com.farez.githubuser.data.local.UserDB
import com.farez.githubuser.data.model.DetailResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<DetailResponse>()
    private var userDAO : FavoriteUserDAO?
    private var userDB : UserDB?
    init {
        userDB = UserDB.getDataBase(application)
        userDAO = userDB?.favUserDAO()
    }

    fun setUSerDetail(username: String) {
        RetrofitClient.apiInstance.getUSerDetail(username)
            .enqueue(object : Callback<DetailResponse> {
                override fun onResponse(
                    call: Call<DetailResponse>, response: Response<DetailResponse>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                    Log.d("gagal", "onFailure: ${t.message}")
                }

            })
    }

    fun getUserDetail(): LiveData<DetailResponse> {
        return user
    }
    fun addToFav(username: String, id: Int, avatar_url: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(username, id, avatar_url)
            userDAO?.addToFavorite(user)
        }
    }

    suspend fun checkUser(id: Int) = userDAO?.checkUser(id)
    fun deleteFromFav(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDAO?.deleteFav(id)
        }
    }
}