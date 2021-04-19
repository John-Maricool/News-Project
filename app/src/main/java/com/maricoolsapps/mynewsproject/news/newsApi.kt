package com.maricoolsapps.mynewsproject.news


import com.maricoolsapps.mynewsproject.news.models.news_model
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface newsApi {
    @GET("everything")
   suspend fun getHeadline(

        @Query("page")page: Int,
        @Query("pageSize")pageSize: Int,
        @Query("q")query: String,
        @Query("apiKey")key: String
    ): news_model

    companion object{
        val BASE_URL = "https://newsapi.org/v2/"
    }
}