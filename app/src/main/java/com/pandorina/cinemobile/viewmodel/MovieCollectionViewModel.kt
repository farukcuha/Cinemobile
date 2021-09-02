package com.pandorina.cinemobile.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.pandorina.cinemobile.data.NetworkResult
import com.pandorina.cinemobile.data.remote.model.Collection
import com.pandorina.cinemobile.data.remote.model.Movie
import com.pandorina.cinemobile.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieCollectionViewModel @Inject constructor(val repository: Repository): ViewModel(){
    val collectionId = MutableLiveData<Int>()

    val getMovieCollection = liveData {
        collectionId.value?.let { repository.getMovieCollection(it) }.let {
            if (it is NetworkResult.Success){
                emit(it.data)
            }
        }
    }
}