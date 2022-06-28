package com.mutawalli.challenge7.di

import android.content.Context
import androidx.room.Room
import com.mutawalli.challenge7.data.local.UserDAO
import com.mutawalli.challenge7.data.local.favorite.MovieDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideMovieDatabase(@ApplicationContext context: Context): com.mutawalli.challenge7.data.api.MovieDatabase {
        return Room.databaseBuilder(context, com.mutawalli.challenge7.data.api.MovieDatabase::class.java, "movie").build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(movieDatabase: com.mutawalli.challenge7.data.api.MovieDatabase): MovieDAO{
        return movieDatabase.movieDao()
    }

    @Singleton
    @Provides
    fun provideUserDao(movieDatabase: com.mutawalli.challenge7.data.api.MovieDatabase): UserDAO{
        return movieDatabase.userDao()
    }
}