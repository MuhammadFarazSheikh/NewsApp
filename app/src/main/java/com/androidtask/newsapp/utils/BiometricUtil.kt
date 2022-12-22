package com.androidtask.newsapp.utils

import android.content.Intent
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import com.androidtask.newsapp.interfaces.BiometricAuthCallback
import java.util.concurrent.Executor

object BiometricUtil {
    var biometricPrompt: BiometricPrompt? = null
    fun onTouchIdOpen(activity: AppCompatActivity, biometricAuthCallback: BiometricAuthCallback) {
        val executor = ContextCompat.getMainExecutor(activity)
        val biometricManager = BiometricManager.from(activity)
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> authUser(
                executor,
                activity,
                biometricAuthCallback
            )
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> //                Toast.makeText(
//                        activity,
//                        "No fingerprint hardware.",
//                        Toast.LENGTH_SHORT
//                ).show();
                biometricAuthCallback.biometricTooManyAttempts()
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> Toast.makeText(
                activity,
                "Fingerprint hardware unavailable.",
                Toast.LENGTH_SHORT
            ).show()
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> activity.startActivity(Intent(Settings.ACTION_FINGERPRINT_ENROLL))
        }
    }

    private fun authUser(
        executor: Executor,
        activity: AppCompatActivity,
        biometricAuthCallback: BiometricAuthCallback
    ) {
        val promptInfo = PromptInfo.Builder()
            .setTitle("Authentication Required")
            .setSubtitle("Important Authentication")
            .setDescription("Please authenticate to be able to use your saved credentials.")
            .setNegativeButtonText("Cancel")
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG) //                .setDeviceCredentialAllowed(true)
            .build()
        biometricPrompt = BiometricPrompt(activity, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    biometricAuthCallback.biometricAuthenticationError(errString.toString())
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    biometricAuthCallback.biometricAuthenticationSucceeded()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    biometricAuthCallback.biometricAuthenticationFailed()
                }
            })
        biometricPrompt!!.authenticate(promptInfo)
    }

    fun isDeviceBiometricSupport(activity: AppCompatActivity?): Boolean {
        val biometricManager = BiometricManager.from(
            activity!!
        )
        return biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS
    }

    fun cancelBioMetricPrompt() {
        if (biometricPrompt != null) {
            biometricPrompt!!.cancelAuthentication()
        }
    }
}