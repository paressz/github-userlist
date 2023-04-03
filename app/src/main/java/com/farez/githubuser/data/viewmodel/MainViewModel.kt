package com.farez.githubuser.data.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.farez.githubuser.api.RetrofitClient
import com.farez.githubuser.data.local.ThemeSetting
import com.farez.githubuser.data.model.User
import com.farez.githubuser.data.model.UserResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val theme: ThemeSetting) : ViewModel() {
    val listUser = MutableLiveData<ArrayList<User>?>()

    fun setSearchUser(query: String) {
        RetrofitClient.apiInstance.getSearchUser(query).enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>, response: Response<UserResponse>
            ) {
                if (response.isSuccessful) {
                    listUser.postValue(response.body()?.items as ArrayList<User>?)
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("failure", "onFailure: ${t.message}")
            }

        })
    }

    fun getSearchUser(): MutableLiveData<ArrayList<User>?> {
        return listUser
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return theme.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            theme.saveThemeSetting(isDarkModeActive)
        }
    }

}