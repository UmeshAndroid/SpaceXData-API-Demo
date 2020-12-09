package com.enhanceit.demo.domain

import com.enhanceit.demo.data.api.ApiService
import com.enhanceit.demo.data.SafeApiRequest
import javax.inject.Inject

class LaunchesRepository @Inject constructor(val apiService: ApiService) : SafeApiRequest() {
    suspend fun getLaunches() = apiRequest {
        apiService.getLaunches()
    }
}