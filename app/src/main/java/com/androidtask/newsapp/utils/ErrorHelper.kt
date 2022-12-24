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
import com.androidtask.newsapp.utils.Constants.FINGER_PRINT_AUTH_ERROR
import com.androidtask.newsapp.utils.Constants.INTERNAL_SERVER_ERROR_TEXT
import com.androidtask.newsapp.utils.Constants.NETWORK_ERROR
import com.androidtask.newsapp.utils.Constants.OPEN_APP_AFTER_30_SECONDS
import com.androidtask.newsapp.utils.Constants.RATE_LIMITED
import com.androidtask.newsapp.utils.Constants.SOMETHING_WENT_WRONG
import com.androidtask.newsapp.utils.Constants.SOURCE_DOESNOT_EXIST
import com.androidtask.newsapp.utils.Constants.SOURCE_TOO_MANY
import com.androidtask.newsapp.utils.Constants.UNEXPECTED_ERROR

//HANDLE NEWS HEADLINES API CALL ERRORS CODE AND DISPLAY MESSAGES FOR USER
inline fun handleApiError(
    failure: Resource.Failure,
): String
{
    if(failure.isNetworkError)
    {
        return NETWORK_ERROR
    }
    else if(failure.errorCode == INTERNAL_SERVER_ERROR)
    {
        return INTERNAL_SERVER_ERROR_TEXT
    }
    else {
        var apiResponseMessage = INTERNAL_SERVER_ERROR_TEXT
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
                SOURCE_DOESNOT_EXIST-> apiResponseMessage = SOMETHING_WENT_WRONG
                UNEXPECTED_ERROR-> apiResponseMessage = INTERNAL_SERVER_ERROR_TEXT
            }
        }
        return apiResponseMessage
    }
}

//HANDLE FINGERPRINT AUTH ERROR CODE AND DISPLAY MESSAGE FOR USER
inline fun handleBiometricAuthError(
    errorCode:Int,
    errorMessage:String
):String
{
    when(errorCode)
    {
        BiometricPrompt.ERROR_NEGATIVE_BUTTON -> return FINGER_PRINT_AUTH_ERROR
        BiometricPrompt.ERROR_LOCKOUT -> return errorMessage+OPEN_APP_AFTER_30_SECONDS
        else -> return FINGER_PRINT_AUTH_ERROR
    }
}