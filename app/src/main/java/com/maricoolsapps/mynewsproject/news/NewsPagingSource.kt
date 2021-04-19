package com.maricoolsapps.mynewsproject.news

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.maricoolsapps.mynewsproject.news.models.Articles
import com.maricoolsapps.mynewsproject.news.models.news_model
import kotlinx.coroutines.flow.collect
import retrofit2.HttpException
import java.io.IOException

class NewsPagingSource (
    private val newsapi: newsApi,
    private val query: String
): PagingSource<Int, Articles>(){


    companion object{
        private val country = "ng"
        private val apiKey = "32736618833543cbbdc0f1c15cdb8b18"
        private val INDEX = 1
    }
    override fun getRefreshKey(state: PagingState<Int, Articles>): Int? {
        return state.anchorPosition
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Articles> {
       val position = params.key ?: INDEX
        return try {
            val response = newsapi.getHeadline(position, params.loadSize, query, apiKey)
            val news = response.articles

            LoadResult.Page(
                data =  news,
                prevKey = if (position == INDEX) null else position-1,
                nextKey = if (news.isEmpty()) null else position + 1
            )
        }catch (e: IOException){
            LoadResult.Error(e)
        }catch (e: HttpException){
            LoadResult.Error(e)
        }
    }

}