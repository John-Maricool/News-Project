package com.maricoolsapps.mynewsproject.news.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerNewsCardItem(
    brush: Brush
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(
                bottom = 8.dp,
                top = 8.dp
            )
            .fillMaxWidth(),
    ) {
        Column {

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(225.dp)
                    .background(brush),
            )
            Spacer(
                modifier = Modifier
                    .padding(5.dp)
            )

            Spacer(
                modifier = Modifier
                    .background(brush)
                    .height(14.dp)
                    .fillMaxWidth(),
            )
            Spacer(
                modifier = Modifier
                    .padding(5.dp)
            )
            Spacer(
                modifier = Modifier
                    .background(brush)
                    .height(10.dp)
                    .fillMaxWidth(0.5f),
            )
        }
    }
}