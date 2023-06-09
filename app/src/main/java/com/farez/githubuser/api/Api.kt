package com.farez.githubuser.api

import com.farez.githubuser.BuildConfig
import com.farez.githubuser.data.model.DetailResponse
import com.farez.githubuser.data.model.User
import com.farez.githubuser.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    @Headers ("Authorization: token " + BuildConfig.KEY)
    fun  getSearchUser (
        @Query("q") query : String
    ) : Call<UserResponse>

    @GET("users/{username}")
    @Headers ("Authorization: token " + BuildConfig.KEY)
    fun getUSerDetail(
        @Path("username") username : String
    ) : Call<DetailResponse>

    @GET("users/{username}/following")
    @Headers ("Authorization: token " + BuildConfig.KEY)
    fun getFollowing (
        @Path("username") username : String
    ) : Call<ArrayList<User>>

    @GET("users/{username}/followers")
    @Headers ("Authorization: token " + BuildConfig.KEY)
    fun getFollower (
        @Path("username") username : String
    ) :  Call<ArrayList<User>>

}