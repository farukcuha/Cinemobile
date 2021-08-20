package com.pandorina.cinemobile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pandorina.cinemobile.data.Repository
import com.pandorina.cinemobile.data.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MoreMoviesViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    var currentResult: Flow<PagingData<Movie>>? = null

    fun getMorePopularMovies() = getResult(repository.getMorePopularMovies())

    fun getMoreTopRatedMovies() = getResult(repository.getMoreTopRatedMovies())

    fun getMoreNowPlayingMovies() = getResult(repository.getMoreNowPlayingMovies())

    fun getMoreUpcomingMovies() = getResult(repository.getMoreUpcomingMovies())

    private fun getResult(flow: Flow<PagingData<Movie>>): Flow<PagingData<Movie>>{
        currentResult?.let {
            return it
        }
        val newResult = flow.cachedIn(viewModelScope)
        currentResult = newResult
        return newResult
    }
}