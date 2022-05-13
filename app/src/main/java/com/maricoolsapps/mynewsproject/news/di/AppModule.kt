package com.maricoolsapps.mynewsproject.news.di

import com.maricoolsapps.mynewsproject.news.appcomponents.ApplicationClass
import com.maricoolsapps.mynewsproject.news.network.newsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationClass(): ApplicationClass {
        return ApplicationClass()
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(newsApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): newsApi =
        retrofit.create(newsApi::class.java)
}