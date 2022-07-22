package ru.autodoc.tz.data.model

import java.io.Serializable
import java.util.*

data class Rep(
    val id: Long = 0,
    val name: String?,
    val full_name: String?,
    val language: String?,
    val description: String?,
    val updated_at: Date?,
    val created_at: Date,
    val owner: Owner,
    val stargazers_count: Int?
) : Serializable
