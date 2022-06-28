package com.mutawalli.challenge7.di

import com.mutawalli.challenge7.datastore.UserDataStoreManager
import com.mutawalli.challenge7.repository.UserRepository
import com.mutawalli.challenge7.ui.favorite.FavoriteViewModelFactory
import com.mutawalli.challenge7.ui.home.HomeViewModelFactory
import com.mutawalli.challenge7.ui.login.LoginViewModelFactory
import com.mutawalli.challenge7.ui.profile.ProfileViewModelFactory
import com.mutawalli.challenge7.ui.profile.UpdateViewModelFactory
import com.mutawalli.challenge7.ui.register.RegisterViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.mutawalli.challenge7.ui.detail.DetailViewModelFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ViewModelFactoryModule {

    @Singleton
    @Provides
    fun provideUpdateViewModelFactory(
        userRepository: UserRepository,
    ): UpdateViewModelFactory {
        return UpdateViewModelFactory(userRepository)
    }

    @Singleton
    @Provides
    fun provideRegisterViewModelFactory(
        userRepository: UserRepository,
    ): RegisterViewModelFactory {
        return RegisterViewModelFactory(userRepository)
    }

    @Singleton
    @Provides
    fun provideProfileViewModelFactory(
        userRepository: UserRepository,
        pref: UserDataStoreManager
    ): ProfileViewModelFactory {
        return ProfileViewModelFactory(userRepository, pref)
    }

    @Singleton
    @Provides
    fun provideLoginViewModelFactory(
        userRepository: UserRepository,
        pref: UserDataStoreManager
    ): LoginViewModelFactory {
        return LoginViewModelFactory(userRepository, pref)
    }

    @Singleton
    @Provides
    fun provideHomeViewModelFactory(
        movieRepository: com.mutawalli.challenge7.data.api.MovieRepository,
        userRepository: UserRepository,
        pref: UserDataStoreManager
    ): HomeViewModelFactory {
        return HomeViewModelFactory(movieRepository, userRepository, pref)
    }

    @Singleton
    @Provides
    fun provideFavoriteViewModelFactory(
        movieRepository: com.mutawalli.challenge7.data.api.MovieRepository,
    ): FavoriteViewModelFactory {
        return FavoriteViewModelFactory(movieRepository)
    }

    @Singleton
    @Provides
    fun provideDetailViewModelFactory(
        movieRepository: com.mutawalli.challenge7.data.api.MovieRepository,
    ): DetailViewModelFactory {
        return DetailViewModelFactory(movieRepository)
    }
}