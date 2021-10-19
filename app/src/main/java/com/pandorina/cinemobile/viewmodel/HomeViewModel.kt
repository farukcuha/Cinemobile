package com.pandorina.cinemobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pandorina.cinemobile.data.remote.NetworkResult
import com.pandorina.cinemobile.data.remote.model.Movie
import com.pandorina.cinemobile.data.remote.model.MovieResponse
import com.pandorina.cinemobile.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    val randomMovieResponse = MutableLiveData<NetworkResult<MovieResponse>>()
    val trendingMovieResponse = MutableLiveData<NetworkResult<MovieResponse>>()
    var job: Job? = null

    fun getRandomMovies(
        withGenres: String?,
        page: Int,
        primaryReleaseYear: String?,
        seconderReleaseYear: String?,
        withOriginalLanguage: String?) {
        job = viewModelScope.launch {
            repository.remoteDataSource.getRandomMovies(
                withGenres, page, primaryReleaseYear, seconderReleaseYear, withOriginalLanguage
            ).collect { randomMovieResponse.value = it }
        }
    }

    fun getTrendingMovies(){
        job = viewModelScope.launch {
            repository.remoteDataSource.getTrendingMovies().collect {
                    trendingMovieResponse.value = it
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job = null
    }
}