package com.enhanceit.demo.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

fun OkHttpClient.Builder.addLoggingInterceptor(): OkHttpClient.Builder {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    this.addInterceptor(loggingInterceptor)
    return this
}