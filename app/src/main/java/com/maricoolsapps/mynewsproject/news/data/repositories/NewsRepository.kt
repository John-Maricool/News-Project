package com.maricoolsapps.mynewsproject.news.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.maricoolsapps.mynewsproject.news.data.source.NewsSource
import com.maricoolsapps.mynewsproject.news.network.newsApi
import com.maricoolsapps.mynewsproject.news.utils.DomainMapperImpl
import javax.inject.Inject

class NewsRepository
@Inject constructor(
    private val newsapi: newsApi,
    private val mapper: DomainMapperImpl
) {

    fun getNewsResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NewsSource(newsapi, query, mapper) }
        ).flow

}