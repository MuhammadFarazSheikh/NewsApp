package com.androidtask.newsapp.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.androidtask.newsapp.R
import com.androidtask.newsapp.models.NewsHeadlineDTO

@Composable
fun setupNewsHeadlineDetailsScreen(
    newsHeadlineDTO: NewsHeadlineDTO
)
{
    Column(
        content = {

            if(!newsHeadlineDTO.urlToImage.isNullOrEmpty() && !newsHeadlineDTO.urlToImage.isNullOrBlank()) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = newsHeadlineDTO.urlToImage,
                        //placeholder = painterResource(R.mipmap.ic_launcher),
                        error = painterResource(id = R.drawable.no_image)
                    ),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(7.dp))
                        .height(100.dp),
                    contentScale = ContentScale.FillBounds
                )
            }

            if(!newsHeadlineDTO.title.isNullOrEmpty() && !newsHeadlineDTO.title.isNullOrBlank()) {
                Text(
                    stringResource(R.string.text_title_label),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.wrapContentHeight()
                        .wrapContentWidth()
                        .padding(0.dp, 8.dp, 0.dp, 0.dp)
                )

                Text(
                    newsHeadlineDTO.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,
                    modifier = Modifier.wrapContentHeight()
                        .wrapContentWidth()
                        .padding(0.dp, 5.dp, 0.dp, 0.dp)
                )
            }

            if(!newsHeadlineDTO.description.isNullOrEmpty() && !newsHeadlineDTO.description.isNullOrBlank()) {
                Text(
                    stringResource(R.string.text_description_label),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.wrapContentHeight()
                        .wrapContentWidth()
                        .padding(0.dp, 8.dp, 0.dp, 0.dp)
                )

                Text(
                    newsHeadlineDTO.description,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,
                    modifier = Modifier.wrapContentHeight()
                        .wrapContentWidth()
                        .padding(0.dp, 5.dp, 0.dp, 0.dp)
                )
            }

            if(!newsHeadlineDTO.content.isNullOrEmpty() && !newsHeadlineDTO.content.isNullOrBlank()) {

                Text(
                    stringResource(R.string.text_content_label),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.wrapContentHeight()
                        .wrapContentWidth()
                        .padding(0.dp, 8.dp, 0.dp, 0.dp)
                )

                Text(
                    newsHeadlineDTO.content,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,
                    modifier = Modifier.wrapContentHeight()
                        .wrapContentWidth()
                        .padding(0.dp, 5.dp, 0.dp, 0.dp)
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .padding(10.dp)
    )
}
