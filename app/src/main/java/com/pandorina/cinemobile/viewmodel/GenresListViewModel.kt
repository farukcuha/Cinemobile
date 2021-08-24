package com.pandorina.cinemobile.viewmodel

import androidx.lifecycle.*
import androidx.paging.LoadState
import com.pandorina.cinemobile.data.NetworkResult
import com.pandorina.cinemobile.data.Repository
import com.pandorina.cinemobile.data.model.GenreResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenresListViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    private val _genreResponse: MutableLiveData<NetworkResult<GenreResponse>> = MutableLiveData()
    val genreResponse: LiveData<NetworkResult<GenreResponse>> = _genreResponse

    fun getGenres(productionType: String){
        genreResponse.value.let {
            viewModelScope.launch {
                repository.getGenres(productionType).collect {
                    _genreResponse.value = it
                }
            }
        }

    }

}