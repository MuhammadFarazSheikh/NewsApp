package com.androidtask.newsapp.domain.models

sealed class Resource<out T> {
    data class Success<out T>(val value: T) : Resource<T>()
    data class Failure(val isNetworkError: Boolean, val errorCode: Int?, val newsHeadlinesApiErrorResponseDTO: NewsHeadlinesApiErrorResponseDTO?) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}