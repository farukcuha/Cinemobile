package com.pandorina.cinemobile.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pandorina.cinemobile.data.remote.NetworkResult
import com.pandorina.cinemobile.data.remote.model.MovieResponse
import com.pandorina.cinemobile.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SimilarViewModel @Inject constructor(val repository: Repository) : ViewModel() {
    private val _similarMovieResponse = MutableLiveData<NetworkResult<MovieResponse>>()
    val similarMovieResponse = _similarMovieResponse
    var job: Job? = null

    fun getSimilarMovies(movieId: Int) {
        similarMovieResponse.value ?: run {
            job = viewModelScope.launch {
                repository.remoteDataSource.getSimilarMovies(movieId).collect {
                    _similarMovieResponse.value = it
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job = null
    }
}