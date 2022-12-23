package com.androidtask.newsapp.utils

import android.content.Context
import androidx.biometric.BiometricPrompt
import com.androidtask.newsapp.utils.Constants.INTERNAL_SERVER_ERROR
import com.androidtask.newsapp.R
import com.androidtask.newsapp.domain.models.Resource
import com.androidtask.newsapp.utils.Constants.API_KEY_DISABLED
import com.androidtask.newsapp.utils.Constants.API_KEY_EXHAUSTED
import com.androidtask.newsapp.utils.Constants.API_KEY_INVALID
import com.androidtask.newsapp.utils.Constants.API_KEY_MISSING
import com.androidtask.newsapp.utils.Constants.API_PARAMETER_INVALID
import com.androidtask.newsapp.utils.Constants.API_PARAMETER_MISSING
import com.androidtask.newsapp.utils.Constants.RATE_LIMITED
import com.androidtask.newsapp.utils.Constants.SOURCE_DOESNOT_EXIST
import com.androidtask.newsapp.utils.Constants.SOURCE_TOO_MANY
import com.androidtask.newsapp.utils.Constants.UNEXPECTED_ERROR

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
            when(data.code)
            {
                API_KEY_DISABLED,
                API_KEY_EXHAUSTED,
                API_KEY_INVALID,
                API_KEY_MISSING,
                API_PARAMETER_INVALID,
                API_PARAMETER_MISSING,
                RATE_LIMITED,
                SOURCE_TOO_MANY,
                SOURCE_DOESNOT_EXIST-> apiResponseMessage = context.getString(R.string.text_something_wrong)
                UNEXPECTED_ERROR-> apiResponseMessage = context.getString(R.string.text_error_internal_server_error)
            }
        }
        return apiResponseMessage
    }
}

inline fun handleBiometricAuthError(
    context: Context,
    errorCode:Int,
    errorMessage:String
):String
{
    when(errorCode)
    {
        BiometricPrompt.ERROR_NEGATIVE_BUTTON -> return context.getString(R.string.error_cancel_auth)
        else -> return errorMessage+context.getString(R.string.error_message_wront_attempts)
    }
}