package ru.autodoc.tz.data.model

import java.io.Serializable

data class Reps(
    val id: Long = 0,
    val name: String,
    val full_name: String
) : Serializable
