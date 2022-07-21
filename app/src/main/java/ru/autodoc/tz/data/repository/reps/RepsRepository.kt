package ru.autodoc.tz.data.repository.reps

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query
import ru.autodoc.tz.data.model.Reps

interface RepsRepository {

    suspend fun getReps(query: String): Flow<PagingData<Reps>>
}