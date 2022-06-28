package com.mutawalli.challenge7.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mutawalli.challenge7.repository.UserRepository
import javax.inject.Inject

class UpdateViewModelFactory @Inject constructor(private val userRepository: UserRepository) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(UpdateProfileViewModel::class.java) -> {
                UpdateProfileViewModel(userRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}