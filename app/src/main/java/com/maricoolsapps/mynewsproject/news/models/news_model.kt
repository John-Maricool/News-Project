package com.maricoolsapps.mynewsproject.news.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class news_model (
     val articles: List<Articles>
): Parcelable