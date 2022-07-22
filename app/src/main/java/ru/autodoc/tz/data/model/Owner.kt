package ru.autodoc.tz.data.model

import java.io.Serializable

data class Owner(
    val avatar_url: String?,
    val login: String
) : Serializable