package com.pandorina.cinemobile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pandorina.cinemobile.data.remote.model.Movie
import com.pandorina.cinemobile.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ByGenresViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private var currentResult: Flow<PagingData<Movie>>? = null

    fun getMoviesByGenres(genreId: Int): Flow<PagingData<Movie>> {
        currentResult?.let {
            return it
        }
        val newResult =
            repository.remoteDataSource.getMoviesByGenres(genreId).cachedIn(viewModelScope)
        currentResult = newResult
        return newResult
    }
}