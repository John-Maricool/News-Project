package com.maricoolsapps.mynewsproject.news.utils

import com.maricoolsapps.mynewsproject.news.data.interfaces.DomainMapper
import com.maricoolsapps.mynewsproject.news.domain.models.News
import com.maricoolsapps.mynewsproject.news.network.models.Articles
import javax.inject.Inject

class DomainMapperImpl
    @Inject constructor(): DomainMapper<Articles, News> {

    override fun mapToDomainModel(model: Articles): News {
        return News(
            title = model.title,
            author = model.author,
            url = model.url,
            time = model.publishedAt,
            content = model.content,
            photo = model.urlToImage,
            sourceName = model.source.name
        )
    }

    fun toDomainList(models: List<Articles>): List<News> {
        return models.map {
            mapToDomainModel(it)
        }
    }
}