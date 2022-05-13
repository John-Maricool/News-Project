package com.maricoolsapps.mynewsproject.news.presentation.ui.news_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.maricoolsapps.mynewsproject.news.data.repositories.NewsRepository
import com.maricoolsapps.mynewsproject.news.domain.models.News
import com.maricoolsapps.mynewsproject.news.utils.NewsCategory
import com.maricoolsapps.mynewsproject.news.utils.getNewsCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel
@Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    val query = mutableStateOf("Catholic")

    val loading = mutableStateOf(true)

    val selectedCategory: MutableState<NewsCategory?> = mutableStateOf(null)

    var news: Flow<PagingData<News>> = flowOf()

    var categoryScrollPosition: Int = 0

    init {
        searchNews()
    }

    fun searchNews() {
        loading.value = true
        try {
            resetSearchState()
            viewModelScope.launch {
                val result = repository.getNewsResults(query.value)
                    .cachedIn(viewModelScope)
                news = result
                loading.value = false
            }
        } catch (e: Exception) {
            loading.value = false
            Log.d("errors", "Error getting data")
        }
    }

    private fun resetSearchState() {
        news = flowOf()
        if (selectedCategory.value?.value != query.value)
            clearSelectedCategory()
    }

    private fun clearSelectedCategory() {
        selectedCategory.value = null
    }

    fun changeQuery(newQuery: String) {
        query.value = newQuery
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getNewsCategory(category)
        selectedCategory.value = newCategory
        changeQuery(category)
    }

    fun onChangeCategoryScrollPosition(position: Int) {
        categoryScrollPosition = position
    }
}