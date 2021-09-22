package com.pandorina.cinemobile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.pandorina.cinemobile.data.local.model.FavoriteMovie
import com.pandorina.cinemobile.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(val repository: Repository) : ViewModel() {
    val favoriteMoviesList = liveData {
        repository.localDataSource.getFavoriteMovies().collect { emit(it) }
    }

    fun deleteFavoriteMovie(favoriteMovie: FavoriteMovie) = viewModelScope.launch {
        repository.localDataSource.deleteFavoriteMovie(favoriteMovie)
    }
}