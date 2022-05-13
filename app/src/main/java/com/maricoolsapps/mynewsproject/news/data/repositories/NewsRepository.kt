package com.maricoolsapps.mynewsproject.news.data.repositories

import com.maricoolsapps.mynewsproject.news.domain.models.News
import com.maricoolsapps.mynewsproject.news.network.newsApi
import com.maricoolsapps.mynewsproject.news.network.newsApi.Companion.apiKey
import com.maricoolsapps.mynewsproject.news.utils.DomainMapperImpl
import javax.inject.Inject

class NewsRepository
@Inject constructor(
    private val newsapi: newsApi,
    private val mapper: DomainMapperImpl
) {

    suspend fun getNews(category: String): List<News> {
        return mapper.toDomainList(
            newsapi.getHeadline(
                page = 1,
                query = category,
                key = apiKey
            ).articles
        )
    }
}