package com.mutawalli.challenge7.di

import com.mutawalli.challenge7.network.MovieApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.mutawalli.challenge7.BuildConfig
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteMovieModule {

    @Singleton
    @Provides
    fun provideMovieRemoteDataSource(apiService: MovieApiService): com.mutawalli.challenge7.data.api.MovieRemoteDataSource {
        return com.mutawalli.challenge7.data.api.MovieRemoteDataSource(
            apiService,
            BuildConfig.API_KEY
        )
    }
}