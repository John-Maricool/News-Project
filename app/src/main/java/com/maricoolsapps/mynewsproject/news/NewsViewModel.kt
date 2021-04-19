package com.maricoolsapps.mynewsproject.news

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn

class NewsViewModel
@ViewModelInject constructor(
    private val repository: NewsRepository
): ViewModel(){

    private val currectCategory = MutableLiveData("sports")

    val news = currectCategory.switchMap { query ->
        repository.getNews(query).cachedIn(viewModelScope)
    }

    fun searchNews(category: String){
        currectCategory.value = category
    }
}