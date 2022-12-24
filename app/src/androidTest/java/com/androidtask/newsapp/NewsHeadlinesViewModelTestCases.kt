package com.androidtask.newsapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.androidtask.newsapp.data.remote.NewsHeadlinesApiService
import com.androidtask.newsapp.domain.models.NewsHeadlineDTO
import com.androidtask.newsapp.domain.models.NewsHeadlinesApiErrorResponseDTO
import com.androidtask.newsapp.domain.models.TopHeadlinesApiResponseDTO
import com.androidtask.newsapp.domain.repositories.NewsHeadlinesRepository
import com.androidtask.newsapp.presentation.viewmodels.NewsHeadlinesViewModel
import com.androidtask.newsapp.utils.Constants.API_KEY_INVALID
import com.androidtask.newsapp.utils.Constants.API_KEY_MISSING
import com.androidtask.newsapp.utils.Constants.UNEXPECTED_ERROR
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

@RunWith(AndroidJUnit4::class)
class NewsHeadlinesViewModelTestCases
{
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var mockWebServer: MockWebServer
    private lateinit var newsHeadlinesViewModel: NewsHeadlinesViewModel
    @Mock
    private lateinit var newsHeadlinesRepository: NewsHeadlinesRepository

    //METHOD CALL BEFORE EVERY TEST CASE
    //CREATE MOCK NEWS HEADLINE REPOSITORY
    //INITIALIZE AND START MOCK WEB SERVER
    //INIT RETROFIT AND ASSIGN API SERVICE TO REPOSITORY
    //ASSIGN REPOSITORY TO NEWS HEADLINE VIEW MODEL
    @Before
    fun setup()
    {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        MockitoAnnotations.openMocks(this)
        newsHeadlinesRepository.newsHeadlinesApiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/").toString())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsHeadlinesApiService::class.java)
        newsHeadlinesViewModel = NewsHeadlinesViewModel(newsHeadlinesRepository)
    }

    //CHECK NEWS HEADLINE API CALL RESPONSE WITH OK STATUS AND DUMMY RESPONSE DATA
    @Test
    fun testGNewsHeadlinesApiWithOkStatus()= runBlocking{
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(
                TopHeadlinesApiResponseDTO("","", arrayListOf<NewsHeadlineDTO>(
                    NewsHeadlineDTO("dummy","dummy","dummy","dummy","dummy","dummy","dummy"),
                    NewsHeadlineDTO("dummy","dummy","dummy","dummy","dummy","dummy","dummy"),
                    NewsHeadlineDTO("dummy","dummy","dummy","dummy","dummy","dummy","dummy")
                ))
            ))
        mockWebServer.enqueue(expectedResponse)


        newsHeadlinesViewModel.callTopHeadlinesApiToGetNewsForSource(BuildConfig.NEWS_SOURCE_ID)
        mockWebServer.takeRequest()
        delay(1000)
        assertThat(newsHeadlinesViewModel.newsHeadlinesListMutableState.value.size>0).isTrue()
    }

    //CHECK API CALL ERROR RESPONSE FOR MISSING API KET AUTHORIZATION ERROR AND GET DUMMY ERROR RESPONSE
    @Test
    fun testNewsHeadlinesApiWithApiKeyMissingErrorStatus() = runBlocking{
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_UNAUTHORIZED)
            .setBody(Gson().toJson(
                NewsHeadlinesApiErrorResponseDTO("",API_KEY_MISSING,"")
            ))
        mockWebServer.enqueue(expectedResponse)


        newsHeadlinesViewModel.callTopHeadlinesApiToGetNewsForSource(BuildConfig.NEWS_SOURCE_ID)
        mockWebServer.takeRequest()
        delay(1000)
        assertThat(newsHeadlinesViewModel.errorMessageDialoguMutableState.value).isNotEmpty()
    }

    //CHECK API CALL ERROR RESPONSE FOR INVALID API KET AUTHORIZATION ERROR AND GET DUMMY ERROR RESPONSE
    @Test
    fun testNewsHeadlinesApiWithApiKeyInvalidErrorStatus() = runBlocking{
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_UNAUTHORIZED)
            .setBody(Gson().toJson(
                NewsHeadlinesApiErrorResponseDTO("", API_KEY_INVALID,"")
            ))
        mockWebServer.enqueue(expectedResponse)
        newsHeadlinesViewModel.callTopHeadlinesApiToGetNewsForSource(BuildConfig.NEWS_SOURCE_ID)
        mockWebServer.takeRequest()
        delay(1000)
        assertThat(newsHeadlinesViewModel.errorMessageDialoguMutableState.value).isNotEmpty()
    }

    //CHECK API CALL ERROR RESPONSE INTERNAL SERVER ERROR AND GET DUMMY ERROR RESPONSE
    @Test
    fun testNewsHeadlinesApiWithInternalServerErrorStatus() = runBlocking{
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
            .setBody(Gson().toJson(
                NewsHeadlinesApiErrorResponseDTO("", UNEXPECTED_ERROR,"")
            ))
        mockWebServer.enqueue(expectedResponse)


        newsHeadlinesViewModel.callTopHeadlinesApiToGetNewsForSource(BuildConfig.NEWS_SOURCE_ID)
        mockWebServer.takeRequest()
        delay(1000)
        assertThat(newsHeadlinesViewModel.errorMessageDialoguMutableState.value).isNotEmpty()
    }

    //SHUT DOWN MOCK WEB SERVER AFTER EVERY TEST CASE COMPLETE
    @After
    fun tearDown()
    {
        mockWebServer.shutdown()
    }
}