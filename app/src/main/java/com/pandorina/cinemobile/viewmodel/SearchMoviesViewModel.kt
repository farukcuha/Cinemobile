package com.pandorina.cinemobile.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pandorina.cinemobile.data.Repository
import com.pandorina.cinemobile.data.model.Movie
import com.pandorina.cinemobile.data.model.MovieResponse
import com.pandorina.cinemobile.data.resource.remote.RemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
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
}