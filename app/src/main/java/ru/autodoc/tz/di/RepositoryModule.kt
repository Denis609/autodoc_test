package ru.autodoc.tz.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.autodoc.tz.data.repository.reps.RepsRepository
import ru.autodoc.tz.data.repository.reps.RepsRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindRepsRepository(
        repsRepositoryImpl: RepsRepositoryImpl
    ): RepsRepository
}