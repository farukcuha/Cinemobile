package com.pandorina.cinemobile.viewmodel

import androidx.lifecycle.*
import com.pandorina.cinemobile.data.repository.Repository
import com.pandorina.cinemobile.data.remote.model.MovieResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val discoverList = MutableLiveData<MovieResponse>()
    val discoverIsLoaded = MutableLiveData(false)

    val popularList = MutableLiveData<MovieResponse>()
    val popularIsLoaded = MutableLiveData(false)

    val topRatedList = MutableLiveData<MovieResponse>()
    val topRatedIsLoaded = MutableLiveData(false)

    val nowPlayingList = MutableLiveData<MovieResponse>()
    val nowPlayingIsLoaded = MutableLiveData(false)

    val upcomingList = MutableLiveData<MovieResponse>()
    val upcomingIsLoaded = MutableLiveData(false)


    fun getDiscoverMovies() {
        viewModelScope.launch {
            try {
                val response = repository.getDiscoverMovies()
                if (response.isSuccessful && response.body() != null) {
                    discoverList.postValue(response.body())
                    discoverIsLoaded.postValue(true)
                } else {
                    discoverIsLoaded.postValue(false)
                }
            }catch (e: Exception){

            }
        }
    }

    fun getPopularMovies() {
        viewModelScope.launch {
            try {
                val response = repository.getPopularMovies()
                if (response.isSuccessful && response.body() != null) {
                    popularList.postValue(response.body())
                    popularIsLoaded.postValue(true)
                } else {
                    popularIsLoaded.postValue(false)
                }
            }catch (e: Exception){

            }
        }
    }

    fun getTopRatedMovies() {
        viewModelScope.launch {
            try {
                val response = repository.getTopRatedMovies()
                if (response.isSuccessful && response.body() != null) {
                    topRatedList.postValue(response.body())
                    topRatedIsLoaded.postValue(true)
                } else {
                    topRatedIsLoaded.postValue(false)
                }
            }catch (e: Exception){

            }
        }
    }

    fun getNowPlayingMovies() {
        viewModelScope.launch {
            try {
                val response = repository.getNowPlayingMovies()
                if (response.isSuccessful && response.body() != null) {
                    nowPlayingList.postValue(response.body())
                    nowPlayingIsLoaded.postValue(true)
                } else {
                    nowPlayingIsLoaded.postValue(false)
                }
            }catch (e: Exception){

            }
        }
    }

    fun getUpcomingMovies() {
        viewModelScope.launch {
            try {
                val response = repository.getUpcomingMovies()
                if (response.isSuccessful && response.body() != null) {
                    upcomingList.postValue(response.body())
                    upcomingIsLoaded.postValue(true)
                } else {
                    upcomingIsLoaded.postValue(false)
                }
            }catch (e: Exception){

            }
        }
    }
}
