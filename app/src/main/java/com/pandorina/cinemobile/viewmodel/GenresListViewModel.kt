package com.pandorina.cinemobile.viewmodel

import androidx.lifecycle.*
import com.pandorina.cinemobile.data.NetworkResult
import com.pandorina.cinemobile.data.repository.Repository
import com.pandorina.cinemobile.data.remote.model.GenreResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
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