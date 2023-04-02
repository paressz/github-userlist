package com.farez.githubuser.data.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.farez.githubuser.api.RetrofitClient
import com.farez.githubuser.data.model.User
import androidx.lifecycle.LiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {
    lateinit var context: Context
    val listFollowers = MutableLiveData<ArrayList<User>>()
    fun setListFollower(username: String) {
        RetrofitClient.apiInstance.getFollower(username)
            .enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>, response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful) {
                        listFollowers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                }

            })
    }

    fun getListFollower(): LiveData<ArrayList<User>> {
        return listFollowers
    }

    fun showErrorToast(context: Context, errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }
}