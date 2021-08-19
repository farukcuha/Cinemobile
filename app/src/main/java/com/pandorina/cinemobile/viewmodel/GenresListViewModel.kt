package com.pandorina.cinemobile.viewmodel

import androidx.lifecycle.*
import com.pandorina.cinemobile.NetworkResult
import com.pandorina.cinemobile.model.Genre
import com.pandorina.cinemobile.model.GenreResponse
import com.pandorina.cinemobile.repository.Repository
import com.pandorina.cinemobile.util.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class GenresListViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    private val _genreResponse: MutableLiveData<NetworkResult<GenreResponse>> = MutableLiveData()
    val genreResponse: LiveData<NetworkResult<GenreResponse>> = _genreResponse

    fun getGenres(productionType: String){
        genreResponse.let {
            viewModelScope.launch {
                repository.getGenres(productionType).collect {
                    _genreResponse.value = it
                }
            }
        }
    }
}