package com.pandorina.cinemobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pandorina.cinemobile.data.remote.NetworkResult
import com.pandorina.cinemobile.data.remote.model.VideoResponse
import com.pandorina.cinemobile.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideosViewModel @Inject constructor(val repository: Repository) : ViewModel() {
    private val _videoList: MutableLiveData<NetworkResult<VideoResponse>> = MutableLiveData()
    val videoList: LiveData<NetworkResult<VideoResponse>> get() = _videoList
    var job: Job? = null

    fun getVideoList(path: String, id: Int) {
        _videoList.value ?: run {
            job = viewModelScope.launch {
                repository.remoteDataSource.getVideos(path, id).collect {
                    _videoList.value = it
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job = null
    }
}