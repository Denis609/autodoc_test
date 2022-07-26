package ru.autodoc.tz.domain.user

interface UserRepository {
    suspend fun getUser(login: String): User
}