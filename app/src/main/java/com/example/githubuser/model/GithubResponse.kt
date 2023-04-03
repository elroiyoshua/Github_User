package com.example.githubuser.model

import com.google.gson.annotations.SerializedName
import kotlin.math.log

data class GithubResponse(

	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean,

	@field:SerializedName("items")
	val items: List<ItemsItem>
)

data class ItemsItem(
	@field:SerializedName("login")
	var login: String,

	@field:SerializedName("id")
	var id: Int,

	@field:SerializedName("avatar_url")
	var avatarUrl: String

)




