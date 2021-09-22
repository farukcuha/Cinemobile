package com.pandorina.cinemobile.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.pandorina.cinemobile.data.local.model.MovieQuery
import com.pandorina.cinemobile.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val searchQuery = MutableLiveData<String>()

    val resultPagingData = searchQuery.switchMap {
        repository.remoteDataSource.getSearchMovies(it).cachedIn(viewModelScope)
    }

    fun searchMovies(query: String) {
        searchQuery.value = query
    }

    val movieQueriesList = repository.localDataSource.getMovieQueries()

    fun insertMovieQuery(query: MovieQuery) = viewModelScope.launch {
        repository.localDataSource.insertMovieQuery(query)
    }

    fun clearMovieQuery() = viewModelScope.launch {
        repository.localDataSource.clearMovieQueries()
    }

}