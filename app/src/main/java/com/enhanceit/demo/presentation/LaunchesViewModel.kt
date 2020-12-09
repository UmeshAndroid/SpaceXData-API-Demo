package com.enhanceit.demo.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enhanceit.demo.utils.APIException
import com.enhanceit.demo.utils.NoInternetException
import com.enhanceit.demo.utils.Resource
import com.enhanceit.demo.data.model.Launches
import com.enhanceit.demo.domain.LaunchesRepository
import kotlinx.coroutines.launch

class LaunchesViewModel @ViewModelInject constructor(
    private val launchesRepository: LaunchesRepository
) : ViewModel() {
    private val _launches = MutableLiveData<Resource<List<Launches>>>()
    val launches: LiveData<Resource<List<Launches>>>
        get() = _launches

    fun fetchLaunches() {
        _launches.postValue(Resource.loading(data = null))
        viewModelScope.launch {
            try {
                val data = launchesRepository.getLaunches()
                _launches.postValue(Resource.success(data))
            } catch (e: APIException) {
                _launches.postValue(Resource.error(data = null, msg = e.message!!))
            } catch (e: NoInternetException) {
                _launches.postValue((Resource.error(data = null, msg = e.message!!)))
            } catch (e: Exception) {
                _launches.postValue(Resource.error(e.toString(), null))
            }
        }
    }
}