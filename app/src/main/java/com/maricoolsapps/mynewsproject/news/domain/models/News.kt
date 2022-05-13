package com.maricoolsapps.mynewsproject.news.domain.models

data class News(
    val title: String?,
    val author: String?,
    val url: String?,
    val time: String?,
    val content: String?,
    val photo: String?,
    val sourceName: String?
)