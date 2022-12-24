package com.androidtask.newsapp.presentation.composescreens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.androidtask.newsapp.BuildConfig

//SETUP TOP APP BAR TO SHOW HEADLINE SOURCE NAME
@Preview
@Composable
fun setupTopAppBar()
{
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(),
        title = {
            Text(
                text = BuildConfig.NEWS_SOURCE,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        },
        backgroundColor = Color.Gray
    )
}