package com.enhanceit.demo.data.api

import com.enhanceit.demo.data.model.Launches
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("v4/launches/")
    suspend fun getLaunches(): Response<List<Launches>>
}