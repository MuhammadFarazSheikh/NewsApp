package com.androidtask.newsapp.domain.models.repositories

import com.androidtask.newsapp.data.remote.NewsHeadlinesApiService
import javax.inject.Inject

class NewsHeadlinesRepository @Inject constructor(private val newsHeadlinesApiService: NewsHeadlinesApiService): BaseRepository()
{
    suspend fun callTopHeadlinesApiToGetNewsForSource(newsSource:String)=safeApiCall {
        newsHeadlinesApiService.getNewsHeadlinesForSource(newsSource)
    }
}