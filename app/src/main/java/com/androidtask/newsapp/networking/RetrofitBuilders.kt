package com.androidtask.newsapp.networking

import com.androidtask.newsapp.Constants.HEADLINES_API_BASE_URL
import com.androidtask.newsapp.interfaces.NewsHeadlinesApiService
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

fun buildClient(): OkHttpClient {
    val dispatcher = Dispatcher()
    dispatcher.maxRequests = 1

    val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .callTimeout(2, TimeUnit.MINUTES)
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .dispatcher(dispatcher)

    return okHttpClientBuilder.build()
}

fun buildRetrofit(baseUrl: String): Retrofit {

    return Retrofit.Builder()
        .client(buildClient())
        .baseUrl(baseUrl)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun buildApiServiceForWeatherUpdates(): NewsHeadlinesApiService = buildRetrofit(HEADLINES_API_BASE_URL).create(NewsHeadlinesApiService::class.java)