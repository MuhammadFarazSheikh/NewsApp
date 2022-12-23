package com.androidtask.newsapp.presentation.activities.screens.newsheadlinelist

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.androidtask.newsapp.domain.models.NewsHeadlineDTO

@Composable
fun setupNewsHeadlineSListScreen(
    navHostController: NavHostController,
    newsHeadlinesListMutableState: MutableState<ArrayList<NewsHeadlineDTO>>
)
{
    newsHeadlinesListMutableState.value?.let { list->
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