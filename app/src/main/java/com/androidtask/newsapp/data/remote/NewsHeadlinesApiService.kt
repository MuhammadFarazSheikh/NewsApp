package com.androidtask.newsapp.data.remote

import com.androidtask.newsapp.utils.Constants.HEADLINES_API_KEY
import com.androidtask.newsapp.domain.models.TopHeadlinesApiResponseDTO
import com.androidtask.newsapp.utils.ApiEndPoints.TOP_HEADLINES
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsHeadlinesApiService
{
    @GET(TOP_HEADLINES)
    @Headers("Authorization:$HEADLINES_API_KEY")
    suspend fun getNewsHeadlinesForSource(@Query("sources") sources:String): TopHeadlinesApiResponseDTO
}