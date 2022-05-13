package com.maricoolsapps.mynewsproject.news.utils


enum class NewsCategory(val value: String) {
    HEALTH("Health"),
    TECHNOLOGY("Technology"),
    POLITICS("Politics"),
    BUSINESS("Business"),
    LIFESTYLE("Lifestyle"),
    CATHOLIC("Catholic"),
    CHRISTIAN("Christian"),
    FOOTBALL("Football"),
    MUSIC("Music"),
    BITCOIN("Bitcoin"),
    NIGERIA("Nigeria"),
    ENTERTAINMENT("Entertainment"),
    WEATHER("Weather"),
    SCIENCE("Science"),
    SPORTS("Sports")
}

fun getAllNewsCategories(): List<NewsCategory> {
    return listOf(
        NewsCategory.HEALTH,
        NewsCategory.TECHNOLOGY,
        NewsCategory.POLITICS,
        NewsCategory.CATHOLIC,
        NewsCategory.BITCOIN,
        NewsCategory.BUSINESS,
        NewsCategory.MUSIC,
        NewsCategory.NIGERIA,
        NewsCategory.ENTERTAINMENT,
        NewsCategory.WEATHER,
        NewsCategory.SPORTS,
        NewsCategory.SCIENCE,
        NewsCategory.FOOTBALL,
        NewsCategory.CHRISTIAN,
        NewsCategory.LIFESTYLE,
    )
}

fun getNewsCategory(value: String): NewsCategory? {
    val map = NewsCategory.values().associateBy(NewsCategory::value)
    return map[value]
}