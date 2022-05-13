package com.maricoolsapps.mynewsproject.news.presentation.ui.news_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.maricoolsapps.mynewsproject.news.appcomponents.ApplicationClass
import com.maricoolsapps.mynewsproject.news.presentation.AppTheme
import com.maricoolsapps.mynewsproject.news.presentation.composables.CircularIndeterminateProgressBar
import com.maricoolsapps.mynewsproject.news.presentation.composables.NewsChip
import com.maricoolsapps.mynewsproject.news.presentation.composables.NewsComposable
import com.maricoolsapps.mynewsproject.news.presentation.composables.searchAppBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalMaterialApi
@AndroidEntryPoint
class NewsListFragment : Fragment() {

    private val viewModel: NewsViewModel by viewModels()

    @Inject
    lateinit var app: ApplicationClass

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                AppTheme(darkTheme = app.isDark.value) {
                    val news = viewModel.news
                    val query = viewModel.query.value
                    val loading = viewModel.loading.value
                    val selectedCategory = viewModel.selectedCategory.value

                    Column(
                        modifier = Modifier.background(MaterialTheme.colors.secondary)
                    ) {
                        //This is the search app bar on the top of the screen.
                        searchAppBar(
                            query = query,
                            changeQuery = { viewModel.changeQuery(query) },
                            searchNews = { viewModel.searchNews() },
                            onToggleTheme = { app.toggleLightTheme() }
                        )
                        // This is where the toggleable chips are
                        NewsChip(
                            categoryScrollPosition = viewModel.categoryScrollPosition,
                            selectedCategory = selectedCategory,
                            onSelectedCategoryChanged = {
                                viewModel.onSelectedCategoryChanged(it.toString())
                            },
                            onChangeCategoryScrollPosition = {
                                viewModel.onChangeCategoryScrollPosition(it)
                            }
                        ) {
                            viewModel.searchNews()
                        }
                        //News Composable
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            NewsComposable(news = news)
                            CircularIndeterminateProgressBar(isDisplayed = loading)
                        }
                    }
                }
            }
        }
    }
}