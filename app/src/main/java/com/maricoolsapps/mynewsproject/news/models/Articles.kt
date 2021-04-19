package com.maricoolsapps.mynewsproject.news.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
    @Parcelize
 data class Articles(
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
): Parcelable
