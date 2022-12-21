package com.androidtask.newsapp.composables

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun setupVerticalList(navHostController: NavHostController)
{
    var list = arrayListOf<String>("Faraz","Faraz","Faraz","Faraz","Faraz","Faraz","Faraz","Faraz","Faraz","Faraz","Faraz","Faraz","Faraz","Faraz","Faraz",
        "Faraz","Faraz","Faraz","Faraz","Faraz","Faraz","Faraz","Faraz","Faraz","Faraz","Faraz","Faraz","Faraz","Faraz",)
    LazyColumn(
        content ={
                 itemsIndexed(list)
                 { index, item ->

                     setupNewsListColumn(navHostController)

                 }
        },
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    )
}