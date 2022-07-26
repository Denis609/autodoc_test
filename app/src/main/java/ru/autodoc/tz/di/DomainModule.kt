package ru.autodoc.tz.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.autodoc.tz.domain.rep.RepRepository
import ru.autodoc.tz.domain.rep.RepGetUseCase
import ru.autodoc.tz.domain.user.UserRepository
import ru.autodoc.tz.domain.user.UserGetUseCase

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {
    @Provides
    fun provideGetRepsUseCase(repsRepository: RepRepository) = RepGetUseCase(repsRepository)

    @Provides
    fun provideGetUserUseCase(usersRepository: UserRepository) = UserGetUseCase(usersRepository)
}