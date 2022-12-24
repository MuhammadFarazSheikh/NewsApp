package com.androidtask.newsapp.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import com.androidtask.newsapp.BuildConfig
import com.androidtask.newsapp.R
import com.androidtask.newsapp.interfaces.BiometricAuthCallback
import com.androidtask.newsapp.presentation.composescreens.setupNavigationComponent
import com.androidtask.newsapp.presentation.composescreens.setupTopAppBar
import com.androidtask.newsapp.utils.*
import com.androidtask.newsapp.presentation.viewmodels.NewsHeadlinesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsHeadlinesActivity : AppCompatActivity(), BiometricAuthCallback {

    private val newsHeadlinesViewModel: NewsHeadlinesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            ComposeView(this).apply {
                setContent {
                    setupUIStructure()
                    displayDialogueForErrorMessage()
                    dislayLoaderAlertDialoge()
                }
            }
        )

        openBiometricAuthOrCallNewsHeadlinesApi()
    }

    //CHECK IF BIOMETRIC HARDWARE AVAILABLE AND CONFIGURED SO OPEN FINGERPRINT AUTH DIALOGE
    //OTHERWISE CALL HEADLINES API TO GET LIST BY SOURCE
    private fun openBiometricAuthOrCallNewsHeadlinesApi()
    {
        if (isDeviceFingerPrintSupported(this@NewsHeadlinesActivity)) {
            openFingerPrintAuthDialoge(
                this@NewsHeadlinesActivity,
                this@NewsHeadlinesActivity
            )
        }
        else
        {
            callApiToGetNewsHeadlinesForSource()
        }
    }

    //CALL TOP HEALINES API TO GET LIST OF NEWS FOR SOURCE
    private fun callApiToGetNewsHeadlinesForSource()
    {
        newsHeadlinesViewModel.showHideLoaderAlertDialoge(true)
        newsHeadlinesViewModel.callTopHeadlinesApiToGetNewsForSource(BuildConfig.NEWS_SOURCE_ID)
    }

    //DISPLAY CONTENT LOADER DIALOGE
    @Composable
    fun dislayLoaderAlertDialoge()
    {
        if(newsHeadlinesViewModel.loaderAlertDialogueMutableState.value)
        {
            openLoaderDialogue()
        }
    }

    //DISPLAY DIALOGE FOR ERROR MESSAGES
    @Composable
    fun displayDialogueForErrorMessage()
    {
        if(!newsHeadlinesViewModel.errorMessageDialoguMutableState.value.equals(getString(R.string.text_close_popup)))
        {
            newsHeadlinesViewModel.showHideLoaderAlertDialoge(false)
            openAlertForMessage(
                title = getString(R.string.text_error_title),
                message = newsHeadlinesViewModel.errorMessageDialoguMutableState.value,
                confirmButtonListener = {
                    finish()
                    newsHeadlinesViewModel.errorMessageDialoguMutableState.value = getString(R.string.text_close_popup)
                }
            )
        }
    }

    // SETUP MAIN UI STRUCTURE TO SHOW ALL DATA
    // SETUP TOPBAR AND NAVIGATION COMPONENT
    @Composable
    @Preview
    fun setupUIStructure()
    {
        Scaffold(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            content = { paddingValues ->
               setupNavigationComponent(newsHeadlinesViewModel)
            },
            topBar = {
                setupTopAppBar()
            }
        )
    }

    // HANDLE FINGERPRINT AUTH ERRORS
    override fun biometricAuthenticationError(errorCode:Int,authenticationError: String) {
        newsHeadlinesViewModel.errorMessageDialoguMutableState.value = handleBiometricAuthError(errorCode, authenticationError)
    }

    //CALL API TO GET NEWS HEADLINES DATA AFTER FINGERPRINT AUTH SUCCESS
    override fun biometricAuthenticationSucceeded() {
        callApiToGetNewsHeadlinesForSource()
    }
}