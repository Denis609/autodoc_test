package ru.autodoc.tz.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    val bio: String?,
    val following: Int,
    val followers: Int,
    val blog: String?,
    @SerializedName("html_url")
    val htmlUrl: String?,
    @SerializedName("twitter_username")
    val twitterUserName: String?
) : Serializable
