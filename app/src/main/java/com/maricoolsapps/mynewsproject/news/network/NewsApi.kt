package com.maricoolsapps.mynewsproject.news.network


import com.maricoolsapps.mynewsproject.news.network.models.news_model
import retrofit2.http.GET
import retrofit2.http.Query

interface newsApi {
    @GET("everything")
    suspend fun getHeadline(
        @Query("page") page: Int,
        @Query("q") query: String,
        @Query("apiKey") key: String
    ): news_model

    companion object {
        val BASE_URL = "https://newsapi.org/v2/"
        val apiKey = "32736618833543cbbdc0f1c15cdb8b18"
    }
}