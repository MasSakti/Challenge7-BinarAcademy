package com.mutawalli.challenge7.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mutawalli.challenge7.repository.UserRepository
import com.mutawalli.challenge7.datastore.UserDataStoreManager
import javax.inject.Inject

class HomeViewModelFactory @Inject constructor(
    private val movieRepository: com.mutawalli.challenge7.data.api.MovieRepository,
    private val userRepository: UserRepository,
    private val pref: UserDataStoreManager
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(movieRepository, userRepository, pref) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}