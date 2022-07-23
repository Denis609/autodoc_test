package ru.autodoc.tz.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class Rep(
    val id: Long = 0,
    val name: String?,
    @SerializedName("full_name")
    val fullName: String?,
    val language: String?,
    val description: String?,
    @SerializedName("updated_at")
    val updatedAt: Date?,
    @SerializedName("created_at")
    val createdAt: Date,
    val owner: Owner,
    @SerializedName("stargazers_count")
    val stargazersCount: Int?
) : Serializable
