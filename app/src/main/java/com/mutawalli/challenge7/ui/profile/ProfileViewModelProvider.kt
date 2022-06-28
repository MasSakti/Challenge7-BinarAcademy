package com.mutawalli.challenge7.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mutawalli.challenge7.repository.UserRepository
import com.mutawalli.challenge7.datastore.UserDataStoreManager
import javax.inject.Inject

class ProfileViewModelFactory @Inject constructor(
    private val userRepository: UserRepository,
    private val pref: UserDataStoreManager
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(pref, userRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}