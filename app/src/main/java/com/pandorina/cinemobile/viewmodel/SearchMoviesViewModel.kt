package com.pandorina.cinemobile.viewmodel

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.pandorina.cinemobile.data.repository.Repository
import com.pandorina.cinemobile.data.local.model.MovieQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMoviesViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    private val searchQuery = MutableLiveData<String>()

    val resultPagingData = searchQuery.switchMap {
        repository.getSearchMovies(it).cachedIn(viewModelScope)
    }

    fun searchMovies(query: String){
        searchQuery.value = query
    }

    val movieQueriesList  = repository.getMovieQueries()

    fun insertMovieQuery(query: MovieQuery) = viewModelScope.launch {
        repository.insertMovieQuery(query)
    }

    fun clearMovieQuery() = viewModelScope.launch {
        repository.clearMovieQueries()
    }

}