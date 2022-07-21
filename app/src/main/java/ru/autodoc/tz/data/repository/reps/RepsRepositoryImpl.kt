package ru.autodoc.tz.data.repository.reps

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query
import ru.autodoc.tz.data.model.Reps
import ru.autodoc.tz.data.paging.RepsPagingDataSource
import ru.autodoc.tz.data.service.RepsApi
import javax.inject.Inject

class RepsRepositoryImpl @Inject constructor(
    private val service: RepsApi
) : RepsRepository {

    override suspend fun getReps(query: String): Flow<PagingData<Reps>> = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = true, prefetchDistance = 2),
        pagingSourceFactory = { RepsPagingDataSource(service, query) }
    ).flow
}