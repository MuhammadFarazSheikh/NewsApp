package com.androidtask.newsapp.interfaces

interface BiometricAuthCallback {
    fun biometricAuthenticationError(errorCode:Int,authenticationError: String)
    fun biometricAuthenticationSucceeded()
}