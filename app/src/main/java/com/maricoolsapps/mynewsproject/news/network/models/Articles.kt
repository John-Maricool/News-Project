package com.maricoolsapps.mynewsproject.news.network.models

import com.google.gson.annotations.SerializedName

data class Articles(
    @SerializedName("source")
    val source: Source,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
)





