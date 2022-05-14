package com.maricoolsapps.mynewsproject.news.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.maricoolsapps.mynewsproject.R
import com.maricoolsapps.mynewsproject.news.domain.models.News
import com.maricoolsapps.mynewsproject.news.presentation.ui.news_list.NewsListFragmentDirections
import com.maricoolsapps.mynewsproject.news.utils.loadPicture
import kotlinx.coroutines.flow.Flow

const val DEFAULT_NEWS_IMAGE = R.drawable.news

@Composable
private fun NewsCard(
    news: News,
    onClick: () -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp
            )
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 8.dp
    ) {
        Column {
            val image =
                news.photo?.let { loadPicture(url = it, defaultImg = DEFAULT_NEWS_IMAGE).value }
            image?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(225.dp),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Image",
                )
            }
            news.title?.let {
                Text(
                    text = it,
                    modifier = Modifier.padding(
                        start = 8.dp, end = 3.dp
                    ),
                    style = MaterialTheme.typography.h3
                )
                Spacer(modifier = Modifier.padding(8.dp))
                news.time?.let { time ->
                    Text(
                        text = time,
                        modifier = Modifier.padding(
                            start = 8.dp, end = 3.dp
                        ),
                        style = MaterialTheme.typography.h5
                    )
                }
            }
        }
    }
}

@Composable
fun NewsComposable(
    news: Flow<PagingData<News>>,
    navController: NavController
) {
    val newsItem: LazyPagingItems<News> = news.collectAsLazyPagingItems()

    LazyColumn {
        items(newsItem) {
            it?.let { newsItem ->
                NewsCard(news = newsItem) {
                    val action =
                        NewsListFragmentDirections.actionNewsListFragmentToNewsDetailFragment(
                            newsItem
                        )
                    navController.navigate(action)
                }
            }
        }

        newsItem.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    items(8) {
                        LoadingNewsListShimmer()
                    }
                }
                loadState.append is LoadState.Loading -> {
                    item {
                        CircularIndeterminateProgressBar(isDisplayed = true)
                    }
                }
                loadState.refresh is LoadState.Error -> {
                    val e = newsItem.loadState.refresh as LoadState.Error
                    item {
                        ShowSnackBar(text = e.error.message.toString())
                    }
                }
                loadState.append is LoadState.Error -> {
                    val e = newsItem.loadState.append as LoadState.Error
                    item {
                        ShowSnackBar(text = e.error.message.toString())
                    }
                }
            }
        }
    }
}