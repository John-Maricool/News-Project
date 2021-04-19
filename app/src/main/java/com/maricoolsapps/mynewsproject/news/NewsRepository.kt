package com.maricoolsapps.mynewsproject.news

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import javax.inject.Inject

class NewsRepository
@Inject constructor(private val newsapi: newsApi){

    fun getNews(category: String) =

        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {NewsPagingSource(newsapi, category)}
        ).liveData

}