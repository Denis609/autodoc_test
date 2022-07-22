package ru.autodoc.tz.data.model

import java.io.Serializable

data class User(
    val bio: String?,
    val following: Int,
    val followers: Int,
    val blog: String?,
    val html_url: String?,
    val twitter_username: String?
) : Serializable
