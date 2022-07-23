package ru.autodoc.tz.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Owner(
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    val login: String
) : Serializable