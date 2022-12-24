package com.androidtask.newsapp.presentation.composescreens.newsheadlinelist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.androidtask.newsapp.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.androidtask.newsapp.utils.Constants.NEWS_HEADLINE_DETAILS_ROUTE
import com.androidtask.newsapp.domain.models.NewsHeadlineDTO
import com.androidtask.newsapp.utils.Keys.NEWS_HEADLINE_DTO

//SETUP SCREEN TO NEWS DEADLINE IN LIST ROW
//@param navHostController USED TO NAVIGATE FROM LIST TO DETAILS SCREEN
//@param newsHeadlineDTO USED TO DISPLAY HEADLINE DATA IN LIST ROW
@Composable
fun setupNewsHeadlinesListRow(
    navHostController: NavHostController,
    newsHeadlineDTO: NewsHeadlineDTO
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp, 10.dp, 10.dp, 0.dp)
            .clickable {
                navHostController.currentBackStackEntry?.savedStateHandle?.set(
                    NEWS_HEADLINE_DTO,
                    newsHeadlineDTO
                )
                navHostController.navigate(
                    NEWS_HEADLINE_DETAILS_ROUTE
                )
            },
        content = {

            Image(
                    painter = rememberAsyncImagePainter(
                    model = newsHeadlineDTO.urlToImage,
                    error = painterResource(id = R.drawable.no_image)
                ),
                contentDescription = "",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterVertically),
                contentScale = ContentScale.FillBounds
            )

            Text(
                text = newsHeadlineDTO.title,
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