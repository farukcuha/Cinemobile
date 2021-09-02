package com.pandorina.cinemobile.viewmodel

import androidx.lifecycle.*
import com.pandorina.cinemobile.data.NetworkResult
import com.pandorina.cinemobile.data.local.model.FavoriteMovie
import com.pandorina.cinemobile.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(val repository: Repository) : ViewModel() {
    val currentMovieId = MutableLiveData<Int>()

    val isFavoriteMovieExist = liveData {
        currentMovieId.value?.let { movieId ->
            repository.checkFavoriteMovieExist(movieId).collect{ emit(it) }
        }
    }

    fun insertFavoriteMovie(movie: FavoriteMovie) = viewModelScope.launch {
        repository.insertFavoriteMovie(movie)
    }

    fun deleteFavoriteMovie(movie: FavoriteMovie) = viewModelScope.launch {
        repository.deleteFavoriteMovie(movie)
    }

    val getMovieDetail = liveData {
        currentMovieId.value?.let { repository.getMovieDetail(it) }.let {
            if (it is NetworkResult.Success){
                emit(it.data)
            }
        }
    }
}