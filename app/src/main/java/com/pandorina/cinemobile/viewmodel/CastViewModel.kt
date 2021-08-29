package com.pandorina.cinemobile.viewmodel

import androidx.lifecycle.*
import com.pandorina.cinemobile.data.repository.Repository
import com.pandorina.cinemobile.data.remote.model.CreditResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CastViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    val castList = MutableLiveData<CreditResponse>()

    fun getCast(id: Int, production_type: String){
        viewModelScope.launch {
            val response = repository.getCast(id, production_type)
            if (response.isSuccessful){
                castList.value = response.body()
            }
        }
    }
}