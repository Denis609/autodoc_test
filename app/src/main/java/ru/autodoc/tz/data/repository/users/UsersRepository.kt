package ru.autodoc.tz.data.repository.users

import ru.autodoc.tz.data.model.User

interface UsersRepository {
    suspend fun getUser(login: String): User
}