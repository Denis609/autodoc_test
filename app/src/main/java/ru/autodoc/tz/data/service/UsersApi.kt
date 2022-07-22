package ru.autodoc.tz.data.service

import retrofit2.http.GET
import retrofit2.http.Path
import ru.autodoc.tz.data.model.User

interface UsersApi {

    @GET("users/{login}")
    suspend fun getUser(
        @Path("login") login: String
    ): User
}