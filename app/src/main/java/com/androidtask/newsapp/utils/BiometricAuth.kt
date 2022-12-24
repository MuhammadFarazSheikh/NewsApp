package com.androidtask.newsapp.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import com.androidtask.newsapp.interfaces.BiometricAuthCallback

//OPEN FINGER PRINT DIALOGE FOR USER AUTHENTICATION
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

//HANLDE USER AUTH STATE EITHER ITS ERROR OR SUCCESS
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

//CHECK IF FINGER PRINT HARDWARE IS AVAILABLE AND FINGERPRINT IS CONFIGURED
inline fun isDeviceFingerPrintSupported(context: Context): Boolean {
    return BiometricManager.from(context)
        .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS
}