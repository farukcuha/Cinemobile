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
class MoviesViewModel @Inject constructor(private val repository: Repository): BaseViewModel() {
    val discoverMovieList = MutableLiveData<NetworkResult<MovieResponse>>()
    val popularMovieList = MutableLiveData<NetworkResult<MovieResponse>>()
    val topRatedMovieList = MutableLiveData<NetworkResult<MovieResponse>>()
    val nowPlayingMovieList = MutableLiveData<NetworkResult<MovieResponse>>()
    val upcomingMovieList = MutableLiveData<NetworkResult<MovieResponse>>()

    fun getMovieGroup(movieGroup: String, list: MutableLiveData<NetworkResult<MovieResponse>>) {
        list.value ?: run {
            job = viewModelScope.launch {
                repository.remoteDataSource.getMovieGroup(movieGroup, 1).collect {
                    list.value = it
                }
            }
            job = null
        }
    }

    fun getDiscoverMovies() {
        discoverMovieList.value ?: run {
            job = viewModelScope.launch {
                repository.remoteDataSource.getDiscoverMovies().collect {
                    discoverMovieList.value = it
                }
            }
            job = null
        }
    }
}
