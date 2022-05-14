package com.maricoolsapps.mynewsproject.news.presentation.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun SearchAppBar(
    query: String,
    changeQuery: (String) -> Unit,
    searchNews: () -> Unit,
    onToggleTheme: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colors.surface,
        elevation = 8.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextField(
                value = query,
                onValueChange = { newValue ->
                    Log.d("valuena", newValue)
                    changeQuery(newValue)
                },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(8.dp)
                    .background(MaterialTheme.colors.surface),
                label = {
                    Text(text = "Search")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    searchNews()
                }),
                textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "contentDescription",
                        modifier = Modifier.clickable(
                            onClick = {searchNews()}
                        )
                    )
                }
            )
            ConstraintLayout(
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                val menu = createRef()
                IconButton(
                    onClick = { onToggleTheme() },
                    modifier = Modifier
                        .constrainAs(menu) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                ) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = null
                    )
                }
            }
        }
    }
}