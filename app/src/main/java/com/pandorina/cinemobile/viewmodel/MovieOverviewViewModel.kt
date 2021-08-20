package com.pandorina.cinemobile.viewmodel

import  androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pandorina.cinemobile.data.Repository
import com.pandorina.cinemobile.data.model.MovieDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieOverviewViewModel @Inject constructor(val repository: Repository): ViewModel() {
    val movieOverview = MutableLiveData<MovieDetail>()

    fun getMovieOverview(movieId: Int){
        viewModelScope.launch {
            val response = repository.getMovieDetail(movieId)
            if (response.isSuccessful){
                movieOverview.postValue(response.body())
            }
        }

    }
}