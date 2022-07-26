package ru.autodoc.tz.domain.user

import javax.inject.Inject

class UserGetUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun execute(login: String): User = userRepository.getUser(login)
}