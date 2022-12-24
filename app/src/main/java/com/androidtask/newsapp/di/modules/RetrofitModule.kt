package com.androidtask.newsapp.di.modules

import android.content.Context
import com.androidtask.newsapp.R
import com.androidtask.newsapp.data.remote.NewsHeadlinesApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideRetrofitInstance(@ApplicationContext context: Context,okHttpClient: OkHttpClient)= Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(context.getString(R.string.api_base_url))
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideNewsHeadlinesApiInterface(retrofit: Retrofit) = retrofit.create(
        NewsHeadlinesApiService::class.java)
}