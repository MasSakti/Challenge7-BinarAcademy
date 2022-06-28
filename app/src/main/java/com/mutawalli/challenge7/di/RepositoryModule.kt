package com.mutawalli.challenge7.di

import com.mutawalli.challenge7.data.local.MovieLocalDataSource
import com.mutawalli.challenge7.data.local.UserDAO
import com.mutawalli.challenge7.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideUserRepository(userDao: UserDAO): UserRepository {
        return UserRepository(userDao)
    }

    @Singleton
    @Provides
    fun provideMovieRepository(
        movieRemoteDataSource: com.mutawalli.challenge7.data.api.MovieRemoteDataSource,
        movieLocalDataSource: MovieLocalDataSource
    ): com.mutawalli.challenge7.data.api.MovieRepository {
        return com.mutawalli.challenge7.data.api.MovieRepository(
            movieRemoteDataSource,
            movieLocalDataSource
        )
    }
}