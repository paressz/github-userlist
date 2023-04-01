package com.farez.githubuser.data.model

import com.google.gson.annotations.SerializedName

data class User(

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("login")
	val login: String? = null
)