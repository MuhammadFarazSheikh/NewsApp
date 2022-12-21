package com.androidtask.newsapp.repositories

import com.androidtask.newsapp.interfaces.NewsHeadlinesApiService
import javax.inject.Inject

class NewsHeadlinesRepository @Inject constructor(private val newsHeadlinesApiService: NewsHeadlinesApiService): BaseRepository()
{
    suspend fun callTopHeadlinesApiToGetNewsForSource(newsSource:String)=safeApiCall {
        newsHeadlinesApiService.getNewsHeadlinesForSource(newsSource)
    }
}