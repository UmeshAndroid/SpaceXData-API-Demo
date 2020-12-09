package com.enhanceit.demo.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Provider

class RetrofitProvider(private val baseUrl: String, private val okHttpClient: OkHttpClient) :
    Provider<Retrofit> {

    override fun get(): Retrofit = Retrofit.Builder().baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
}