package ru.autodoc.tz.data.rep

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.autodoc.tz.data.util.PagedResponse
import ru.autodoc.tz.domain.rep.Rep

interface RepApi {

    @GET("search/repositories")
    suspend fun findAllByQuery(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 20
    ): Response<PagedResponse<Rep>>
}