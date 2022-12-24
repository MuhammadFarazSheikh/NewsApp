package com.androidtask.newsapp.presentation.composescreens.newsheadlinelist

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.androidtask.newsapp.domain.models.NewsHeadlineDTO
import com.androidtask.newsapp.presentation.viewmodels.NewsHeadlinesViewModel

//SETUP SCREEN TO SHOW LIST OF NEWS HEADLINES
//@param navHostController USED TO NAVIGATE FROM LIST TO DETAILS SCREEN
//@param newsHeadlinesViewModel USED TO SET LIST OF HEADLINES FROM API RESPONSE
@Composable
fun setupNewsHeadlineSListScreen(
    navHostController: NavHostController,
    newsHeadlinesViewModel: NewsHeadlinesViewModel
)
{
    newsHeadlinesViewModel.newsHeadlinesListMutableState.value?.let { list->
        newsHeadlinesViewModel.showHideLoaderAlertDialoge(false)
        if(list.size>0)
        {
            LazyColumn(
                content ={
                    itemsIndexed(list)
                    { index, item ->
                        setupNewsHeadlinesListRow(navHostController,item)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
        }
    }
}