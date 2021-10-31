package com.pandorina.cinemobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pandorina.cinemobile.data.remote.NetworkResult
import com.pandorina.cinemobile.data.remote.model.CreditResponse
import com.pandorina.cinemobile.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CastViewModel @Inject constructor(private val repository: Repository): BaseViewModel() {
    private val _castList = MutableLiveData<NetworkResult<CreditResponse>>()
    val castList: LiveData<NetworkResult<CreditResponse>> = _castList

    fun getCast(id: Int, production_type: String) {
        _castList.value ?: run {
            job = viewModelScope.launch {
                repository.remoteDataSource.getCast(id, production_type).collect {
                    _castList.value = it
                }
            }
        }
    }
}