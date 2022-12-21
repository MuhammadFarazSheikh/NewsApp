package com.androidtask.newsapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SourceDTO(
    val id:String,
    val name:String
) : Parcelable