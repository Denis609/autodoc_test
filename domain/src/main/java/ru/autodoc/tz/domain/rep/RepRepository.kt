package ru.autodoc.tz.domain.rep

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface RepRepository {
    suspend fun findAllByQuery(query: String): Flow<PagingData<Rep>>
}