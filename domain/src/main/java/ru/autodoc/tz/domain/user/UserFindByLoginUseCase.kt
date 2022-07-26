package ru.autodoc.tz.domain.user

import javax.inject.Inject

class UserFindByLoginUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun execute(login: String): User = userRepository.findByLogin(login)
}