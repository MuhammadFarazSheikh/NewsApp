package com.androidtask.newsapp.interfaces

interface BiometricAuthCallback {
    fun biometricAuthenticationError(authenticationError: String?)
    fun biometricAuthenticationSucceeded()
}