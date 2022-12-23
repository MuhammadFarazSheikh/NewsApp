package com.androidtask.newsapp.di.modules

import com.androidtask.newsapp.utils.Constants.HEADLINES_API_BASE_URL
import com.androidtask.newsapp.data.remote.NewsHeadlinesApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule
{
    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor()=HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor)= OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .callTimeout(2, TimeUnit.MINUTES)
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .dispatcher(Dispatcher().apply {
            maxRequests = 1
        }).build()

    @Singleton
    @Provides
    fun provideRetrofitInstance(okHttpClient: OkHttpClient)= Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(HEADLINES_API_BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideNewsHeadlinesApiInterface(retrofit: Retrofit) = retrofit.create(
        NewsHeadlinesApiService::class.java)
}