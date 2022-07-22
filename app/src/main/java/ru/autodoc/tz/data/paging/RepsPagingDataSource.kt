package ru.autodoc.tz.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import ru.autodoc.tz.data.model.Rep
import ru.autodoc.tz.data.service.RepsApi
import java.io.IOException
import javax.inject.Inject

class RepsPagingDataSource @Inject constructor(
    private val service: RepsApi,
    private val query: String
) : PagingSource<Int, Rep>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Rep> {
        val pageNumber = params.key ?: 1
        return try {
            val response = service.getReps(query, pageNumber)
            val pagedResponse = response.body()
            val data = pagedResponse?.items

            val nextPageNumber: Int? = if (data.isNullOrEmpty()) {
                null
            } else {
                pageNumber + 1
            }

            LoadResult.Page(
                data = data.orEmpty(),
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Rep>): Int? =
        state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
}