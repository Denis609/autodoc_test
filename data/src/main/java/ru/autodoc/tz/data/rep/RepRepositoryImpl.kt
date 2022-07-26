package ru.autodoc.tz.data.rep

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.autodoc.tz.domain.rep.Rep
import ru.autodoc.tz.data.rep.paging.RepPagingDataSource
import ru.autodoc.tz.domain.rep.RepRepository
import javax.inject.Inject

class RepRepositoryImpl @Inject constructor(
    private val service: RepApi
) : RepRepository {

    override suspend fun getReps(query: String): Flow<PagingData<Rep>> = Pager(
        config = PagingConfig(pageSize = 20, prefetchDistance = 5),
        pagingSourceFactory = {
            RepPagingDataSource(service = service, query = query)
        }
    ).flow
}