package com.androidtask.newsapp.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.androidtask.newsapp.Constants.NEWS_HEADLINE_DETAILS_ROUTE
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun setupNewsListColumn(navHostController: NavHostController)
{
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp, 10.dp, 10.dp, 0.dp)
            .clickable {
                       navHostController.navigate(NEWS_HEADLINE_DETAILS_ROUTE)
            },
        content = {

            GlideImage(
                model = "https://ichef.bbci.co.uk/news/1024/branded_news/18E0/production/_128086360_gettyimages-1068966376.jpg",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                contentDescription = "",
                contentScale = ContentScale.FillBounds
            )

            Text(
                text = "Toronto: Eight teenage girls charged with killing man",
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp, 0.dp, 0.dp, 0.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    )
}