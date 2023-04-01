package com.farez.githubuser.data.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.farez.githubuser.api.RetrofitClient
import com.farez.githubuser.data.model.DetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {
    val user = MutableLiveData<DetailResponse>()

    fun  setUSerDetail(username : String) {
         RetrofitClient.apiInstance
             .getUSerDetail(username)
             .enqueue(object: Callback<DetailResponse>{
                 override fun onResponse(
                     call: Call<DetailResponse>,
                     response: Response<DetailResponse>
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
    fun getUserDetail() : LiveData<DetailResponse> {
        return user
    }
}