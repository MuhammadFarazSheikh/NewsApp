package com.androidtask.newsapp.composables

import android.util.Log
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.androidtask.newsapp.Constants.NEWS_HEADLINES_LIST_ROUTE
import com.androidtask.newsapp.Constants.NEWS_HEADLINE_DETAILS_ROUTE
import com.androidtask.newsapp.models.NewsHeadlineDTO

@Composable
fun setupNavigationComponent(newsHeadlinesListMutableState:MutableState<ArrayList<NewsHeadlineDTO>>)
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
                    setupNewsHeadlineDetailsScreen(
                        navController
                    )
                },
                route = NEWS_HEADLINE_DETAILS_ROUTE
            )
        } ,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    )
}