package com.e.commerce.data.remote.retrofit

import android.annotation.SuppressLint
import android.content.Context
import com.e.commerce.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

// Created by Hussein_Mohammad on 5/2/2021.

@SuppressLint("StaticFieldLeak")
@Module
@InstallIn(SingletonComponent::class)
object ApiControl {
    private const val BASE_URL = "https://student.valuxapps.com/api/"
    private var okHttpClient: OkHttpClient = OkHttpClient()
    private const val REQUEST_TIMEOUT: Int = 60

    @Provides
    @Singleton
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(initOkHttp())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun initOkHttp(): OkHttpClient {
        okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(HeaderInterceptor())
            addInterceptor(HttpLoggingInterceptor { message ->
                Timber.i("Data::${message}")
            }.apply {
                level = HttpLoggingInterceptor.Level.BODY
            })

            connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
        }.build()

        return okHttpClient
    }

    @Provides
    @Singleton
    fun apiService(): ApiService {
        return getRetrofit().create(ApiService::class.java)
    }
}