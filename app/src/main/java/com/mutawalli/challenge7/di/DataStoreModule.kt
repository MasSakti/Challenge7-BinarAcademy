package com.mutawalli.challenge7.di

import android.content.Context
import com.mutawalli.challenge7.datastore.UserDataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {
    @Singleton
    @Provides
    fun provideUserDataStoreManager(@ApplicationContext context: Context): UserDataStoreManager {
        return UserDataStoreManager(context)
    }
}