package com.maricoolsapps.mynewsproject.news.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.maricoolsapps.mynewsproject.news.domain.models.News
import com.maricoolsapps.mynewsproject.news.network.newsApi
import com.maricoolsapps.mynewsproject.news.utils.DomainMapperImpl

class NewsSource(
    val api: newsApi,
    val query: String,
    val mapper: DomainMapperImpl
) : PagingSource<Int, News>() {

    override fun getRefreshKey(state: PagingState<Int, News>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
        return try {
            val nextPage = params.key ?: 1
            val newsList = api.getHeadline(
                page = nextPage,
                query = query,
                key = newsApi.apiKey
            )
            LoadResult.Page(
                data = mapper.toDomainList(newsList.articles),
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (newsList.articles.isEmpty()) null else nextPage + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}