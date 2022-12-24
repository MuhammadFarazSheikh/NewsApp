package com.androidtask.newsapp

import android.app.Application
import com.androidtask.newsapp.domain.models.NewsHeadlinesApiErrorResponseDTO
import com.androidtask.newsapp.domain.models.Resource
import com.androidtask.newsapp.utils.Constants
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
import com.androidtask.newsapp.utils.handleApiError
import com.androidtask.newsapp.utils.handleBiometricAuthError
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ErrorHelperTestCases
{
    //VERIFY NETWROK ERROR MESSAGE FOR API CALL ERROR RESPONE
    @Test
    fun isNetworkError()
    {
        assertThat(
            handleApiError(
                Resource.Failure(true,null,null))
        ).isEqualTo(NETWORK_ERROR)
    }

    //VERIFY INTERNAL SERVER ERROR MESSAGE FOR API CALL ERROR RESPONE
    @Test
    fun isInternalServerError()
    {
        assertThat(
            handleApiError(
                Resource.Failure(
                    false,
                    null,
                    NewsHeadlinesApiErrorResponseDTO(
                        "error",
                        UNEXPECTED_ERROR,
                        "")
                ))
        ).isEqualTo(INTERNAL_SERVER_ERROR_TEXT)
    }

    //VERIFY EXPECTED ERROR MESSAGE FOR USER WHEN API KEY DIABLED
    @Test
    fun apiKeyDisabledError()
    {
        assertThat(
            handleApiError(Resource.Failure(false, null, NewsHeadlinesApiErrorResponseDTO("error", API_KEY_DISABLED, "")))
        ).isEqualTo(SOMETHING_WENT_WRONG)
    }

    //VERIFY EXPECTED ERROR MESSAGE FOR USER WHEN API KEY EXHAUSTED
    @Test
    fun apiKeyExhaustedError()
    {
        assertThat(
            handleApiError(Resource.Failure(false, null, NewsHeadlinesApiErrorResponseDTO("error", API_KEY_EXHAUSTED, "")))
        ).isEqualTo(SOMETHING_WENT_WRONG)
    }

    //VERIFY EXPECTED ERROR MESSAGE FOR USER WHEN API KEY INVALID
    @Test
    fun apiKeyInvalidError()
    {
        assertThat(
            handleApiError(Resource.Failure(false, null, NewsHeadlinesApiErrorResponseDTO("error", API_KEY_INVALID, "")))
        ).isEqualTo(SOMETHING_WENT_WRONG)
    }

    //VERIFY EXPECTED ERROR MESSAGE FOR USER WHEN API KEY MISSING
    @Test
    fun apiKeyMissingError()
    {
        assertThat(
            handleApiError(Resource.Failure(false, null, NewsHeadlinesApiErrorResponseDTO("error", API_KEY_MISSING, "")))
        ).isEqualTo(SOMETHING_WENT_WRONG)
    }

    //VERIFY EXPECTED ERROR MESSAGE FOR USER WHEN API PARAMETER INVALID
    @Test
    fun apiParamterInvalidError()
    {
        assertThat(
            handleApiError(Resource.Failure(false, null, NewsHeadlinesApiErrorResponseDTO("error", API_PARAMETER_INVALID, "")))
        ).isEqualTo(SOMETHING_WENT_WRONG)
    }

    //VERIFY EXPECTED ERROR MESSAGE FOR USER WHEN API PARAMETER MISSING
    @Test
    fun apiParamterMissingError()
    {
        assertThat(
            handleApiError(Resource.Failure(false, null, NewsHeadlinesApiErrorResponseDTO("error", API_PARAMETER_MISSING, "")))
        ).isEqualTo(SOMETHING_WENT_WRONG)
    }

    //VERIFY EXPECTED ERROR MESSAGE FOR USER WHEN API RATE LIMITED
    @Test
    fun apiRateLimitedError()
    {
        assertThat(
            handleApiError(Resource.Failure(false, null, NewsHeadlinesApiErrorResponseDTO("error", RATE_LIMITED, "")))
        ).isEqualTo(SOMETHING_WENT_WRONG)
    }

    //VERIFY EXPECTED ERROR MESSAGE FOR USER IF TOO MANY SOURCES PASSED IN API
    @Test
    fun apiSourceTooManyError()
    {
        assertThat(
            handleApiError(Resource.Failure(false, null, NewsHeadlinesApiErrorResponseDTO("error", SOURCE_TOO_MANY, "")))
        ).isEqualTo(SOMETHING_WENT_WRONG)
    }

    //VERIFY EXPECTED ERROR MESSAGE FOR USER WHEN SOURCE DOES NOT EXIST
    @Test
    fun apiSourceDoesNotExistError()
    {
        assertThat(
            handleApiError(Resource.Failure(false, null, NewsHeadlinesApiErrorResponseDTO("error", SOURCE_DOESNOT_EXIST, "")))
        ).isEqualTo(SOMETHING_WENT_WRONG)
    }

    //VERIFY EXPECTED ERROR MESSAGE FOR USER WHEN API RETURNS UNEXPECTED ERROR
    @Test
    fun apiUnexpectedError()
    {
        assertThat(
            handleApiError(Resource.Failure(false, null, NewsHeadlinesApiErrorResponseDTO("error", UNEXPECTED_ERROR, "")))
        ).isEqualTo(INTERNAL_SERVER_ERROR_TEXT)
    }

    //VERIFY EXPECTED ERROR MESSAGE FOR USER WHEN FINGER PRINT DIALOGE CANCELLED BUTTON IS PRESSED
    @Test
    fun verifyMessageForUserCancelledFingerprintDialoge()
    {
        assertThat(handleBiometricAuthError(13, "")).isNotEmpty()
    }

    //VERIFY EXPECTED ERROR MESSAGE FOR USER WHEN FINGER PRINT TOO MANY WRONG ATTEMPTS
    @Test
    fun verifyMessageForTooManyWrongAttemptsOfFingerprint()
    {
        assertThat(handleBiometricAuthError(7, "")).isNotEmpty()
    }
}