package com.android.imagesearch.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiBuilder {
    private const val BASE_URL = "https://www.flickr.com/services/rest/"
    private val retrofit: Retrofit? = null
    private val httpClient: OkHttpClient? = null

    private fun getRetrofit() = retrofit ?: Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(getHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun getHttpClient() = httpClient ?: OkHttpClient.Builder()
        .addInterceptor(getHttpLogInterceptor())
        .connectTimeout(5, TimeUnit.SECONDS)
        .build()

    private fun getHttpLogInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}