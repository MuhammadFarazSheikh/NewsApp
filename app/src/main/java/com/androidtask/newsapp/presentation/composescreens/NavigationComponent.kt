package com.androidtask.newsapp.presentation.activities.screens

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
import com.androidtask.newsapp.presentation.activities.screens.newsheadlinedetails.setupNewsHeadlineDetailsScreen
import com.androidtask.newsapp.presentation.activities.screens.newsheadlinelist.setupNewsHeadlineSListScreen
import com.androidtask.newsapp.utils.Keys.NEWS_HEADLINE_DTO

@Composable
fun setupNavigationComponent(
    newsHeadlinesListMutableState:MutableState<ArrayList<NewsHeadlineDTO>>,
)
{
    var navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NEWS_HEADLINES_LIST_ROUTE,
        builder = {
            composable(
                content = {
                    setupNewsHeadlineSListScreen(navController,newsHeadlinesListMutableState)
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