package com.androidtask.newsapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.androidtask.newsapp.domain.models.Resource
import com.androidtask.newsapp.domain.models.repositories.NewsHeadlinesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsHeadlinesViewModel @Inject constructor(
    private val newsHeadlinesRepository: NewsHeadlinesRepository
    ): ViewModel()
{
        fun callTopHeadlinesApiToGetNewsForSource(newsSource:String)= liveData{
            emit(Resource.Loading)
            emit(newsHeadlinesRepository.callTopHeadlinesApiToGetNewsForSource(newsSource))
        }
}