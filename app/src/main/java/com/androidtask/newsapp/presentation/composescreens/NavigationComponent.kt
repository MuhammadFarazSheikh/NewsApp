package com.androidtask.newsapp.presentation.composescreens

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.androidtask.newsapp.utils.Constants.NEWS_HEADLINES_LIST_ROUTE
import com.androidtask.newsapp.utils.Constants.NEWS_HEADLINE_DETAILS_ROUTE
import com.androidtask.newsapp.domain.models.NewsHeadlineDTO
import com.androidtask.newsapp.presentation.composescreens.newsheadlinedetails.setupNewsHeadlineDetailsScreen
import com.androidtask.newsapp.presentation.composescreens.newsheadlinelist.setupNewsHeadlineSListScreen
import com.androidtask.newsapp.presentation.viewmodels.NewsHeadlinesViewModel
import com.androidtask.newsapp.utils.Keys.NEWS_HEADLINE_DTO

//SETUP NAVIGATION COMPONENT TO DISPLAY NEWS HEADLINES LIST AND DETAILS SCREENS
//@param newsHeadlinesViewModel USED TO PASS TO HEADLINES LIST SCREEN TO POPULATE LIST
@Composable
fun setupNavigationComponent(
    newsHeadlinesViewModel: NewsHeadlinesViewModel,
)
{
    var navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NEWS_HEADLINES_LIST_ROUTE,
        builder = {
            composable(
                content = {
                    setupNewsHeadlineSListScreen(navController,newsHeadlinesViewModel)
                },
                route = NEWS_HEADLINES_LIST_ROUTE
            )
            composable(
                content = { backStackEntry->
                    var newsHeadlineDTO = navController?.
                    previousBackStackEntry?.
                    savedStateHandle?.get<NewsHeadlineDTO>(NEWS_HEADLINE_DTO)
                    newsHeadlineDTO?.let {
                        setupNewsHeadlineDetailsScreen(
                            it,
                            navController
                        )
                    }
                },
                route = NEWS_HEADLINE_DETAILS_ROUTE
            )
        } ,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    )
}