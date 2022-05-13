package com.maricoolsapps.mynewsproject.news.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.maricoolsapps.mynewsproject.news.utils.NewsCategory
import com.maricoolsapps.mynewsproject.news.utils.getAllNewsCategories
import kotlinx.coroutines.launch

@Composable
private fun NewsCategoryChip(
    category: String,
    isSelected: Boolean = false,
    onSelectedCategoryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit
) {
    Surface(
        modifier = Modifier.padding(end = 8.dp),
        elevation = 8.dp,
        shape = MaterialTheme.shapes.medium,
        color = if (!isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.background
    ) {
        Row(
            modifier = Modifier.toggleable(
                value = isSelected,
                onValueChange = {
                    onSelectedCategoryChanged(category)
                    onExecuteSearch()
                }
            )
        ) {
            Text(
                text = category,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun NewsChip(
    categoryScrollPosition: Int,
    selectedCategory: NewsCategory?,
    onSelectedCategoryChanged: (String?) -> Unit,
    onChangeCategoryScrollPosition: (Int) -> Unit,
    searchNews: () -> Unit
) {
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    LazyRow(
        state = scrollState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, bottom = 8.dp)
            .background(MaterialTheme.colors.secondary),
    ) {
        coroutineScope.launch {
            scrollState.scrollToItem(categoryScrollPosition)
        }
        for (category in getAllNewsCategories()) {
            item {
                NewsCategoryChip(category = category.value,
                    isSelected = selectedCategory == category,
                    onSelectedCategoryChanged = {
                        onSelectedCategoryChanged(it)
                        onChangeCategoryScrollPosition(
                            scrollState.firstVisibleItemIndex
                        )
                    },
                    onExecuteSearch = {
                        searchNews()
                    })
            }
        }
    }
}