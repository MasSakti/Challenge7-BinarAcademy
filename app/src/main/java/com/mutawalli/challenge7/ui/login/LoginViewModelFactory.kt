package com.mutawalli.challenge7.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mutawalli.challenge7.datastore.UserDataStoreManager
import com.mutawalli.challenge7.repository.UserRepository
import javax.inject.Inject

class LoginViewModelFactory @Inject constructor(
    private val userRepository: UserRepository,
    private val pref: UserDataStoreManager
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(userRepository, pref) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}