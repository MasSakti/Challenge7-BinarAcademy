package com.mutawalli.challenge7.di

import com.mutawalli.challenge7.data.local.MovieLocalDataSource
import com.mutawalli.challenge7.data.local.favorite.MovieDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalMovieModule {
    @Singleton
    @Provides
    fun provideMovieLocalDataSource(movieDao: MovieDAO): MovieLocalDataSource {
        return MovieLocalDataSource(movieDao)
    }
}