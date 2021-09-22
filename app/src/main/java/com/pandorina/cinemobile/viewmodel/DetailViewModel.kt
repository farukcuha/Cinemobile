package com.pandorina.cinemobile.viewmodel

import androidx.lifecycle.*
import com.pandorina.cinemobile.data.remote.NetworkResult
import com.pandorina.cinemobile.data.local.model.FavoriteMovie
import com.pandorina.cinemobile.data.remote.model.MovieDetail
import com.pandorina.cinemobile.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(val repository: Repository) : ViewModel() {
    val currentMovieId = MutableLiveData<Int>()
    private val _movieDetail: MutableLiveData<NetworkResult<MovieDetail>> = MutableLiveData()
    val movieDetail: LiveData<NetworkResult<MovieDetail>> get() = _movieDetail
    var job: Job? = null

    val isFavoriteMovieExist = liveData {
        currentMovieId.value?.let { movieId ->
            repository.localDataSource.checkFavoriteMovieExist(movieId).collect { emit(it) }
        }
    }

    fun insertFavoriteMovie(movie: FavoriteMovie) = viewModelScope.launch {
        repository.localDataSource.insertFavoriteMovie(movie)
    }

    fun deleteFavoriteMovie(movie: FavoriteMovie) = viewModelScope.launch {
        repository.localDataSource.deleteFavoriteMovie(movie)
    }

    fun getMovieDetail() {
        movieDetail.value ?: run {
            currentMovieId.value?.let {
                job = viewModelScope.launch {
                    repository.remoteDataSource.getMovieDetail(it).collect {
                        _movieDetail.value = it
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job = null
    }
}