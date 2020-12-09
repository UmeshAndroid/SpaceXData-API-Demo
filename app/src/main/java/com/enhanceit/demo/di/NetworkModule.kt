package com.enhanceit.demo.di

import android.content.Context
import androidx.annotation.NonNull
import com.enhanceit.demo.*
import com.enhanceit.demo.data.api.ApiService
import com.enhanceit.demo.data.api.NetworkConnectionInterceptor
import com.enhanceit.demo.data.api.OkHttpClientProvider
import com.enhanceit.demo.data.api.RetrofitProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    fun provideNetworkConnectionInterceptor(@ApplicationContext appContext: Context) =
        NetworkConnectionInterceptor(appContext)

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    fun provideOkHttpClientProvider(networkConnectionInterceptor: NetworkConnectionInterceptor) =
        OkHttpClientProvider(
            networkConnectionInterceptor
        )

    @Provides
    fun provideOKHttpClient(okHttpClientProvider: OkHttpClientProvider) = okHttpClientProvider.get()

    @Provides
    fun provideRetrofit(BASE_URL: String, okHttpClient: OkHttpClient) =
        RetrofitProvider(
            BASE_URL,
            okHttpClient = okHttpClient
        ).get()

    @Provides
    @Singleton
    fun provideApiService(@NonNull retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}