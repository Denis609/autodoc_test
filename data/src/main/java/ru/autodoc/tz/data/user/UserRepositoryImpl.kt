package ru.autodoc.tz.data.user

import ru.autodoc.tz.domain.user.User
import ru.autodoc.tz.domain.user.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: UserApi
) : UserRepository {
    override suspend fun findByLogin(login: String): User = api.findByLogin(login = login)
}