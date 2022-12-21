package com.androidtask.newsapp.utils

import com.androidtask.newsapp.networking.Resource

inline fun handleApiError(failure: Resource.Failure)
{
    if(failure.isNetworkError)
    {
        return
    }
}