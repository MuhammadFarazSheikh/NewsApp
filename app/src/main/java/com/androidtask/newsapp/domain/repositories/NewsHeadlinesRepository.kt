package com.androidtask.newsapp.domain.repositories

import com.androidtask.newsapp.data.remote.NewsHeadlinesApiService
import javax.inject.Inject

open class NewsHeadlinesRepository @Inject constructor(var newsHeadlinesApiService: NewsHeadlinesApiService): BaseRepository()
{
    //CALL API TO GET NEWS HEADLINES LIST BY SOURCE NAME
    suspend fun callTopHeadlinesApiToGetNewsForSource(newsSource:String)=safeApiCall {
        newsHeadlinesApiService.getNewsHeadlinesForSource(newsSource)
    }
}