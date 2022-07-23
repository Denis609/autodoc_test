package ru.autodoc.tz.data.repository.users

import ru.autodoc.tz.data.model.User
import ru.autodoc.tz.data.service.UsersApi
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val api: UsersApi
) : UsersRepository {
    override suspend fun getUser(login: String): User = api.getUser(login = login)
}