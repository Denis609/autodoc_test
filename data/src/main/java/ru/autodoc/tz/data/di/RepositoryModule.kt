package ru.autodoc.tz.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.autodoc.tz.domain.rep.RepRepository
import ru.autodoc.tz.data.rep.RepRepositoryImpl
import ru.autodoc.tz.domain.user.UserRepository
import ru.autodoc.tz.data.user.UserRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRepRepository(
        repRepositoryImpl: RepRepositoryImpl
    ): RepRepository

    @Binds
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository
}