package com.androidtask.newsapp.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TopHeadlinesApiResponseDTO(
    val status:String,
    val totalResults:String,
    val articles:ArrayList<NewsHeadlineDTO>
) : Parcelable