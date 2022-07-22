package ru.autodoc.tz.data.repository.reps

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.autodoc.tz.data.model.Rep

interface RepsRepository {
    suspend fun getReps(query: String): Flow<PagingData<Rep>>
}