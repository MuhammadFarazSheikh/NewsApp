package com.androidtask.newsapp

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.androidtask.newsapp.utils.isDeviceFingerPrintSupported
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BiometricAuthTestCases
{
    //CHECK IF FINGER PRINT AVAILABLE AND CONFIGURED
    @Test
    fun checkIfBiometricAuthAvailableAndConfigured()
    {
        assertThat(isDeviceFingerPrintSupported(ApplicationProvider.getApplicationContext())).isTrue()
    }

    //CHECK IF FINGER PRINT IS NOT AVAILABLE AND NOT CONFIGURED
    @Test
    fun checkIfBiometricAuthNotAvailableAndConfigured()
    {
        assertThat(isDeviceFingerPrintSupported(ApplicationProvider.getApplicationContext())).isFalse()
    }
}