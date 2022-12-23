package com.androidtask.newsapp.presentation.activities

import android.hardware.biometrics.BiometricManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.DialogFragment
import com.androidtask.newsapp.BuildConfig
import com.androidtask.newsapp.R
import com.androidtask.newsapp.presentation.activities.screens.setupNavigationComponent
import com.androidtask.newsapp.interfaces.BiometricAuthCallback
import com.androidtask.newsapp.domain.models.NewsHeadlineDTO
import com.androidtask.newsapp.domain.models.Resource
import com.androidtask.newsapp.presentation.activities.screens.setupTopAppBar
import com.androidtask.newsapp.utils.*
import com.androidtask.newsapp.presentation.viewmodels.NewsHeadlinesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsHeadlinesActivity : AppCompatActivity(), BiometricAuthCallback {

    private lateinit var loaderAlertDialogueMutableState:MutableState<Boolean>
    private lateinit var errorMessageDialoguMutableState:MutableState<String>
    private val newsHeadlinesViewModel: NewsHeadlinesViewModel by viewModels()
    private lateinit var newsHeadlinesListMutableState:MutableState<ArrayList<NewsHeadlineDTO>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
        openBiometricAuthOrCallNewsHeadlinesApi()
        setContentView(
            ComposeView(this).apply {
                setContent {
                    setupUIStructure()
                    displayDialogueForErrorMessage()
                    dislayLoaderAlertDialoge()
                }
            }
        )
    }

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

    private fun callApiToGetNewsHeadlinesForSource()
    {
        newsHeadlinesViewModel
            .callTopHeadlinesApiToGetNewsForSource(BuildConfig.NEWS_SOURCE_ID)
            .observe(this)
            { result->
                showHideLoaderAlertDialoge(false)
                when(result)
                {
                    is Resource.Success->
                    {
                        newsHeadlinesListMutableState.value = result.value.articles
                    }
                    is Resource.Failure->{
                        errorMessageDialoguMutableState.value=handleApiError(result,this)
                    }
                    is Resource.Loading->
                    {
                        showHideLoaderAlertDialoge(true)
                    }
                }
            }
    }

    private fun showHideLoaderAlertDialoge(isShow:Boolean)
    {
        loaderAlertDialogueMutableState.value = isShow
    }

    private fun init()
    {
        loaderAlertDialogueMutableState = mutableStateOf(false)
        errorMessageDialoguMutableState = mutableStateOf(getString(R.string.text_close_popup))
        newsHeadlinesListMutableState = mutableStateOf(arrayListOf())
    }

    @Composable
    fun dislayLoaderAlertDialoge()
    {
        if(loaderAlertDialogueMutableState.value)
        {
            openLoaderDialogue()
        }
    }

    @Composable
    fun displayDialogueForErrorMessage()
    {
        if(!errorMessageDialoguMutableState.value.equals(getString(R.string.text_close_popup)))
        {
            openAlertForMessage(
                title = getString(R.string.text_error_title),
                message = errorMessageDialoguMutableState.value,
                confirmButtonListener = {
                    finish()
                    errorMessageDialoguMutableState.value = getString(R.string.text_close_popup)
                }
            )
        }
    }

    // SETUP MAIN UI STRUCTURE TO SHOW ALL DATA
    @Composable
    @Preview
    fun setupUIStructure()
    {
        Scaffold(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            content = { paddingValues ->
               setupNavigationComponent(newsHeadlinesListMutableState)
            },
            topBar = {
                setupTopAppBar()
            }
        )
    }

    override fun biometricAuthenticationError(errorCode:Int,authenticationError: String) {
        errorMessageDialoguMutableState.value = handleBiometricAuthError(this,errorCode, authenticationError)
    }

    override fun biometricAuthenticationSucceeded() {
        callApiToGetNewsHeadlinesForSource()
    }
}