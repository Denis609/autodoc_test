package ru.autodoc.tz.data.model

import java.io.Serializable

data class PagedResponse<T> (
    val items: List<T> = listOf()
) : Serializable