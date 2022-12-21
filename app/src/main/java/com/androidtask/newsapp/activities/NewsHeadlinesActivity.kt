package com.androidtask.newsapp.activities

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
import com.androidtask.newsapp.composables.setupNavigationComponent
import com.androidtask.newsapp.composables.setupTopBar
import com.androidtask.newsapp.models.NewsHeadlineDTO
import com.androidtask.newsapp.models.TopHeadlinesApiResponseDTO
import com.androidtask.newsapp.networking.Resource
import com.androidtask.newsapp.utils.handleApiError
import com.androidtask.newsapp.viewmodels.NewsHeadlinesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsHeadlinesActivity : AppCompatActivity() {

    private lateinit var errorMessageDialoguMutableState:MutableState<String>
    private val newsHeadlinesViewModel:NewsHeadlinesViewModel by viewModels()
    private lateinit var newsHeadlinesListMutableState:MutableState<ArrayList<NewsHeadlineDTO>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
        callApiToGetNewsHeadlinesForSource()

        setContentView(
            ComposeView(this).apply {
                setContent {
                    setupUIStructure()
                }
            }
        )
    }

    private fun callApiToGetNewsHeadlinesForSource()
    {
        newsHeadlinesViewModel
            .callTopHeadlinesApiToGetNewsForSource("bbc-news")
            .observe(this)
            { result->
                when(result)
                {
                    is Resource.Success->
                    {
                        newsHeadlinesListMutableState.value = result.value.articles
                    }
                    is Resource.Failure->{
                        handleApiError(result)
                    }
                    is Resource.Loading->
                    {

                    }
                }
            }
    }

    private fun init()
    {
        errorMessageDialoguMutableState = mutableStateOf("")
        newsHeadlinesListMutableState = mutableStateOf(arrayListOf())
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
                setupTopBar()
            }
        )
    }
}