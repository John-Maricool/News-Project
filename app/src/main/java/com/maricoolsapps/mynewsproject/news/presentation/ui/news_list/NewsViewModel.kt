package com.maricoolsapps.mynewsproject.news.presentation.ui.news_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maricoolsapps.mynewsproject.news.data.repositories.NewsRepository
import com.maricoolsapps.mynewsproject.news.domain.models.News
import com.maricoolsapps.mynewsproject.news.utils.NewsCategory
import com.maricoolsapps.mynewsproject.news.utils.getNewsCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel
@Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    val query = mutableStateOf("Catholic")

    val loading = mutableStateOf(false)

    val selectedCategory: MutableState<NewsCategory?> = mutableStateOf(null)

    val news: MutableState<List<News>> = mutableStateOf(arrayListOf())

    var categoryScrollPosition: Int = 0

    init {
        searchNews()
    }

    fun searchNews() {
        loading.value = false
        try {
            viewModelScope.launch {
                val result = repository.getNews(query.value)
                news.value = result

            }
        } catch (e: Exception) {
            Log.d("errors", "Error getting data")
        }
    }

    fun changeQuery(newQuery: String) {
        query.value = newQuery
    }

    fun onSelectedCategoryChanged(category: String){
        val newCategory = getNewsCategory(category)
        selectedCategory.value = newCategory
        changeQuery(category)
    }

    fun onChangeCategoryScrollPosition(position: Int){
        categoryScrollPosition = position
    }
}