package ru.autodoc.tz.domain.user

interface UserRepository {
    suspend fun findByLogin(login: String): User
}