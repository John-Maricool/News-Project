package com.maricoolsapps.mynewsproject.news.presentation.ui.news_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.maricoolsapps.mynewsproject.news.presentation.composables.NewsCard
import com.maricoolsapps.mynewsproject.news.presentation.composables.NewsCategoryChip
import com.maricoolsapps.mynewsproject.news.utils.getAllNewsCategories
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsListFragment : Fragment() {

    private val viewModel: NewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val news = viewModel.news.value
                val query = viewModel.query.value

                val selectedCategory = viewModel.selectedCategory.value
                Column {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth(),
                        color = Color.White,
                        elevation = 8.dp
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextField(
                                value = query,
                                onValueChange = { newValue ->
                                    viewModel.changeQuery(newValue)
                                },
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .padding(8.dp)
                                    .background(Color.White),
                                label = {
                                    Text(text = "Search")
                                },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Search
                                ),
                                textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                                keyboardActions = KeyboardActions(onSearch = { viewModel.searchNews() }),
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Filled.Search,
                                        contentDescription = "contentDescription"
                                    )
                                }
                            )
                        }
                    }
                    val scrollState = rememberLazyListState()
                    val coroutineScope = rememberCoroutineScope()

                    LazyRow(
                        state = scrollState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, bottom = 8.dp)
                            .background(Color.White),
                    ) {
                        coroutineScope.launch {
                            scrollState.scrollToItem(viewModel.categoryScrollPosition)
                        }
                        for (category in getAllNewsCategories()) {
                            item {
                                NewsCategoryChip(category = category.value,
                                    isSelected = selectedCategory == category,
                                    onSelectedCategoryChanged = {
                                        viewModel.onSelectedCategoryChanged(it)
                                        viewModel.onChangeCategoryScrollPosition(
                                            scrollState.firstVisibleItemIndex
                                        )
                                    },
                                    onExecuteSearch = {
                                        viewModel.searchNews()
                                    })
                            }
                        }
                    }
                    LazyColumn {
                        itemsIndexed(
                            items = news
                        ) { index, news ->
                            NewsCard(news = news) {

                            }
                        }
                    }
                }
            }
        }
    }
}