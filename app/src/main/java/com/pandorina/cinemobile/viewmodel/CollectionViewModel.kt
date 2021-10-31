package com.pandorina.cinemobile.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pandorina.cinemobile.data.remote.NetworkResult
import com.pandorina.cinemobile.data.remote.model.Collection
import com.pandorina.cinemobile.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(val repository: Repository): BaseViewModel(){
    private val _movieCollectionList = MutableLiveData<NetworkResult<Collection>>()
    val movieCollectionList = _movieCollectionList

    fun getMovieCollection(collectionId: Int) {
        movieCollectionList.value ?: run {
            job = viewModelScope.launch {
                repository.remoteDataSource.getMovieCollection(collectionId).collect {
                    _movieCollectionList.value = it
                }
            }
        }
    }
}