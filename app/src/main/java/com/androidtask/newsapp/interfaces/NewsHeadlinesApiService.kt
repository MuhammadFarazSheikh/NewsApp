package com.androidtask.newsapp.interfaces

import com.androidtask.newsapp.Constants.HEADLINES_API_KEY
import com.androidtask.newsapp.models.TopHeadlinesApiResponseDTO
import com.androidtask.newsapp.networking.ApiEndPoints.TOP_HEADLINES
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsHeadlinesApiService
{
    @GET(TOP_HEADLINES)
    @Headers("Authorization:$HEADLINES_API_KEY")
    suspend fun getNewsHeadlinesForSource(@Query("sources") sources:String):TopHeadlinesApiResponseDTO
}