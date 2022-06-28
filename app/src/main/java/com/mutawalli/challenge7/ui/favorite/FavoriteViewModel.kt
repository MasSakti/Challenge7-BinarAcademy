package com.mutawalli.challenge7.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.mutawalli.challenge7.data.local.favorite.MovieEntity
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(private val repository: com.mutawalli.challenge7.data.api.MovieRepository) : ViewModel() {
    private var _getListFavorite = MutableLiveData<List<MovieEntity>>()
    val getListFavorite: LiveData<List<MovieEntity>> get() = _getListFavorite

    fun getUser() {
        viewModelScope.launch {
            _getListFavorite.postValue(repository.getFavoriteUser())
        }
    }
}