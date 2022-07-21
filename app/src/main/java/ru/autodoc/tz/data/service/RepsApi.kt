package ru.autodoc.tz.data.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.autodoc.tz.data.model.PagedResponse
import ru.autodoc.tz.data.model.Reps

interface RepsApi {

    @GET("search/repositories")
    suspend fun getReps(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 20
    ): Response<PagedResponse<Reps>>

}