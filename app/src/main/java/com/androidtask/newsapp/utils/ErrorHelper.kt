package com.androidtask.newsapp.utils

import android.content.Context
import com.androidtask.newsapp.Constants.INTERNAL_SERVER_ERROR
import com.androidtask.newsapp.R
import com.androidtask.newsapp.networking.Resource

inline fun handleApiError(
    failure: Resource.Failure,
    context: Context
): String
{
    if(failure.isNetworkError)
    {
        return context.getString(R.string.text_error_network)
    }
    else if(failure.errorCode == INTERNAL_SERVER_ERROR)
    {
        return context.getString(R.string.text_error_internal_server_error)
    }
    else {
        var apiResponseMessage = context.getString(R.string.text_error_internal_server_error)
        failure.newsHeadlinesApiErrorResponseDTO?.let { data->
            apiResponseMessage=data.message
        }
        return apiResponseMessage
    }
}