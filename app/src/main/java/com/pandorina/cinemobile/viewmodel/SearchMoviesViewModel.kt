package com.pandorina.cinemobile.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pandorina.cinemobile.data.Repository
import com.pandorina.cinemobile.data.model.Movie
import com.pandorina.cinemobile.data.model.MovieQuery
import com.pandorina.cinemobile.data.model.MovieResponse
import com.pandorina.cinemobile.data.resource.remote.RemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import java.lang.Exception
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

    val movieQueriesList  = flow {
        repository.getMovieQueries().collect { emit(it) }
    }

    fun insertMovieQuery(query: MovieQuery) = viewModelScope.launch {
        repository.insertMovieQuery(query)
    }

    fun clearMovieQuery() = viewModelScope.launch {
        repository.clearMovieQueries()
    }

}