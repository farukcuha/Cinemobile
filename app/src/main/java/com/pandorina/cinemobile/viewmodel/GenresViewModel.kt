package com.pandorina.cinemobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pandorina.cinemobile.data.remote.NetworkResult
import com.pandorina.cinemobile.data.remote.model.GenreResponse
import com.pandorina.cinemobile.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenresViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _genreResponse: MutableLiveData<NetworkResult<GenreResponse>> = MutableLiveData()
    val genreResponse: LiveData<NetworkResult<GenreResponse>> = _genreResponse
    var job: Job? = null

    init {
        _genreResponse.value ?: run {
            job = viewModelScope.launch {
                repository.remoteDataSource.getGenres().collect {
                    _genreResponse.value = it
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job = null
    }

}