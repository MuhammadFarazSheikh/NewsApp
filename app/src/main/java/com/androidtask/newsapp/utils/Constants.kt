package com.androidtask.newsapp.utils;

object Constants {
    const val CLOSE_POPUP = "Close popup" //TEXT USED TO CLOSE MESSAGE POPUP

    //NAVIGATION COMPONENT ROUTES
    const val NEWS_HEADLINES_LIST_ROUTE = "newsHeadlinesList"
    const val NEWS_HEADLINE_DETAILS_ROUTE = "newsHeadlineDetails"
    //HEADLINES API KEY
    const val HEADLINES_API_KEY: String = "912f14dc93ab4888a145a73dfeac7072"
    //NEWS HEADLINES API CALL ERROR CODES
    const val INTERNAL_SERVER_ERROR = 500
    const val API_KEY_DISABLED = "apiKeyDisabled"
    const val API_KEY_EXHAUSTED = "apiKeyExhausted"
    const val API_KEY_INVALID = "apiKeyInvalid"
    const val API_KEY_MISSING = "apiKeyMissing"
    const val API_PARAMETER_INVALID = "parameterInvalid"
    const val API_PARAMETER_MISSING = "parametersMissing"
    const val RATE_LIMITED = "rateLimited"
    const val SOURCE_TOO_MANY = "sourcesTooMany"
    const val SOURCE_DOESNOT_EXIST = "sourceDoesNotExist"
    const val UNEXPECTED_ERROR = "unexpectedError"
    //API CALL ERROR TEXT FOR USER EXPERIENCE
    const val NETWORK_ERROR = "Network error.\nCheck your internet connection or try again later."
    const val INTERNAL_SERVER_ERROR_TEXT = "Internal server error\nTry again later."
    const val SOMETHING_WENT_WRONG = "Something went wrong\nPlease try again later."

    //FINGERPRINT AUTH ERRORS TEXT
    const val FINGER_PRINT_AUTH_ERROR = "Fingerprint auth is required you cannot proceed."
    const val OPEN_APP_AFTER_30_SECONDS = "Open app after 30 seconds to authenticate."
}