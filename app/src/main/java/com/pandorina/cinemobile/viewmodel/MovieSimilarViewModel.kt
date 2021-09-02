package com.pandorina.cinemobile.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.pandorina.cinemobile.data.NetworkResult
import com.pandorina.cinemobile.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieSimilarViewModel @Inject constructor(val repository: Repository): ViewModel(){
    val movieId = MutableLiveData<Int>()

    val similarMovieList = liveData {
        repository.getSimilarMovies(movieId.value).let {
            if (it is NetworkResult.Success){
                emit(it.data)
            }
        }
    }
}