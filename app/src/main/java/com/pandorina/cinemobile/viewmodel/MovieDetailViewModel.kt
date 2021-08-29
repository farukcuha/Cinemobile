package com.pandorina.cinemobile.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pandorina.cinemobile.data.local.model.FavoriteMovie
import com.pandorina.cinemobile.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(val repository: Repository) : ViewModel() {
    val currentMovieId = MutableLiveData<Int>()
    val isFavoriteMovieExist = MutableLiveData<Boolean>()

    fun checkFavoriteMovieIsExist() = viewModelScope.launch {
        currentMovieId.value?.let { movieId ->
            repository.checkFavoriteMovieExist(movieId).collect {
                isFavoriteMovieExist.value = it
            }
        }
    }

    fun insertFavoriteMovie(movie: FavoriteMovie) = viewModelScope.launch {
        repository.insertFavoriteMovie(movie)
    }

    fun deleteFavoriteMovie(movie: FavoriteMovie) = viewModelScope.launch {
        repository.deleteFavoriteMovie(movie)
    }
}