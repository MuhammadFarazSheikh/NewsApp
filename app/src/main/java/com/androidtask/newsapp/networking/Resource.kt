package com.androidtask.newsapp.networking

import com.androidtask.newsapp.models.NewsHeadlinesApiErrorResponseDTO
import okhttp3.ResponseBody

sealed class Resource<out T> {
    data class Success<out T>(val value: T) : Resource<T>()
    data class Failure(val isNetworkError: Boolean, val errorCode: Int?, val newsHeadlinesApiErrorResponseDTO: NewsHeadlinesApiErrorResponseDTO?) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}