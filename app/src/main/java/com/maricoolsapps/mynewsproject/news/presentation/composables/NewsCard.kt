package com.maricoolsapps.mynewsproject.news.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.maricoolsapps.mynewsproject.R
import com.maricoolsapps.mynewsproject.news.domain.models.News
import com.maricoolsapps.mynewsproject.news.utils.loadPicture

const val DEFAULT_NEWS_IMAGE = R.drawable.news

@Composable
fun NewsCard(
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
            .clickable(onClick = onClick)
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
                    style = MaterialTheme.typography.h5
                )
                Spacer(modifier = Modifier.padding(8.dp))
                news.time?.let { time ->
                    Text(
                        text = time,
                        modifier = Modifier.padding(
                            start = 8.dp, end = 3.dp
                        ),
                        style = MaterialTheme.typography.h6
                    )
                }
            }
        }
    }
}