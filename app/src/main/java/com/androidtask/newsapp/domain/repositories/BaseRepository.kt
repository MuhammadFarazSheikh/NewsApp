package com.androidtask.newsapp.domain.repositories

import com.androidtask.newsapp.domain.models.NewsHeadlinesApiErrorResponseDTO
import com.androidtask.newsapp.domain.models.Resource
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

//BASE CLASS TO HANDLE API CALL SUCCESS AND ERRORS
abstract class BaseRepository {
    private lateinit var coroutineScope: CoroutineScope

    suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                coroutineScope = this
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        Resource.Failure(false, throwable.code(), Gson().fromJson(throwable.response()?.errorBody()?.string(),
                            NewsHeadlinesApiErrorResponseDTO::class.java))
                    }
                    else -> {
                        Resource.Failure(true, null, null)
                    }
                }
            }
        }
    }
}