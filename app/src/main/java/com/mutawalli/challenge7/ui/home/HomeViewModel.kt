package com.mutawalli.challenge7.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.mutawalli.challenge7.data.api.Movies
import com.mutawalli.challenge7.data.local.UserEntity
import com.mutawalli.challenge7.repository.UserRepository
import com.mutawalli.challenge7.datastore.UserDataStoreManager
import com.mutawalli.challenge7.resource.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository: com.mutawalli.challenge7.data.api.MovieRepository,
    private val userRepository: UserRepository,
    private val pref: UserDataStoreManager
) : ViewModel() {
    private var _popular = MutableLiveData<List<Movies>>()
    val popular: LiveData<List<Movies>> get() = _popular

    private var _errorStatus = MutableLiveData<String?>()
    val errorStatus: LiveData<String?> get() = _errorStatus

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private var _userData = MutableLiveData<Resource<UserEntity>>()
    val userData: LiveData<Resource<UserEntity>> get() = _userData

    init {
        getPopularMovie()
    }


    fun getPopularMovie() {
        viewModelScope.launch {
            try {
                _loading.value = true
                _popular.value = repository.getPopularMovie()
            } catch (error: com.mutawalli.challenge7.data.api.ErrorMovie) {
                _errorStatus.value = error.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun onSnackbarShown() {
        _errorStatus.value = null
    }

    fun userData(id: Int) {
        viewModelScope.launch {
            Log.d("thread", Thread.currentThread().name)
            _userData.value = Resource.loading(null)
            try {
                val data = userRepository.getUser(id)
                _userData.value = Resource.success(data, 0)
                Log.d("thread", Thread.currentThread().name)
            } catch (exception: Exception) {
                Log.d("thread", Thread.currentThread().name)
                _userData.value = Resource.error(null, exception.message!!)
            }
        }
    }

    fun getIdUser(): LiveData<Int>{
        return pref.getId().asLiveData()
    }
}