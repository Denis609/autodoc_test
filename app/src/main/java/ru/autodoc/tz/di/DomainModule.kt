package ru.autodoc.tz.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.autodoc.tz.domain.rep.RepRepository
import ru.autodoc.tz.domain.rep.RepFindAllByQueryUseCase
import ru.autodoc.tz.domain.user.UserRepository
import ru.autodoc.tz.domain.user.UserFindByLoginUseCase

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {
    @Provides
    fun provideGetRepsUseCase(repRepository: RepRepository) = RepFindAllByQueryUseCase(repRepository)

    @Provides
    fun provideGetUserUseCase(userRepository: UserRepository) = UserFindByLoginUseCase(userRepository)
}