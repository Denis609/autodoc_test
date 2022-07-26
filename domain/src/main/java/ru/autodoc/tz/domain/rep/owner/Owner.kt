package ru.autodoc.tz.domain.rep.owner

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Owner(
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    val login: String
) : Serializable