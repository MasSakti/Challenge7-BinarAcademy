package com.mutawalli.challenge7.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutawalli.challenge7.data.local.UserEntity
import com.mutawalli.challenge7.utils.Event
import kotlinx.coroutines.launch
import com.mutawalli.challenge7.repository.UserRepository

class UpdateProfileViewModel(private val repository: UserRepository) : ViewModel() {
    private val _saved = MutableLiveData<Event<Boolean>>()
    val saved: LiveData<Event<Boolean>> get() = _saved
    fun update(user: UserEntity
    ) {
        if (user.email.isEmpty() || user.username.isEmpty() || user.fullname.isEmpty() || user.ttl.isEmpty() || user.address.isEmpty() || user.password.isEmpty() || user.id == 0 || user.image.isEmpty()) {
            _saved.value = Event(false)
        } else {
            _saved.value = Event(true)
            viewModelScope.launch {
                repository.update(user)
            }
        }
    }
}