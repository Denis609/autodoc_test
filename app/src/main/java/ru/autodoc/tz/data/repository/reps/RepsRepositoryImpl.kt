package ru.autodoc.tz.data.repository.reps

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.autodoc.tz.data.model.Rep
import ru.autodoc.tz.data.paging.RepsPagingDataSource
import ru.autodoc.tz.data.service.RepsApi
import javax.inject.Inject

class RepsRepositoryImpl @Inject constructor(
    private val service: RepsApi
) : RepsRepository {

    override suspend fun getReps(query: String): Flow<PagingData<Rep>> = Pager(
        config = PagingConfig(pageSize = 20, prefetchDistance = 5),
        pagingSourceFactory = {
            RepsPagingDataSource(service, query)
        }
    ).flow
}