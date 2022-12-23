package com.androidtask.newsapp.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import com.androidtask.newsapp.interfaces.BiometricAuthCallback

inline fun openFingerPrintAuthDialoge(
    activity: AppCompatActivity,
    biometricAuthCallback: BiometricAuthCallback
) {
    when (BiometricManager.from(activity)
        .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
        BiometricManager.BIOMETRIC_SUCCESS -> authUser(
            activity,
            biometricAuthCallback
        )
        BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {}
        BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {}
    }
}

inline fun authUser(
    activity: AppCompatActivity,
    biometricAuthCallback: BiometricAuthCallback
) {
    BiometricPrompt(
        activity,
        ContextCompat.getMainExecutor(activity),
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                biometricAuthCallback.biometricAuthenticationError(errorCode,errString.toString())
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                biometricAuthCallback.biometricAuthenticationSucceeded()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
            }
        }).authenticate(
        PromptInfo.Builder().apply {
            setTitle("Authentication Required")
            setSubtitle("Important Authentication")
            setDescription("Please authenticate to continue")
            setNegativeButtonText("Cancel")
            setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
        }.build())
}

inline fun isDeviceFingerPrintSupported(activity: AppCompatActivity): Boolean {
    return BiometricManager.from(activity)
        .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS
}