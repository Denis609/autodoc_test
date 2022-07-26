package ru.autodoc.tz.data.user

import retrofit2.http.GET
import retrofit2.http.Path
import ru.autodoc.tz.domain.user.User

interface UserApi {

    @GET("users/{login}")
    suspend fun findByLogin(
        @Path("login") login: String
    ): User
}