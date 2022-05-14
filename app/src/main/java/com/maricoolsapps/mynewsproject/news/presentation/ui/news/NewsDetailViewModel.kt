package com.maricoolsapps.mynewsproject.news.presentation.ui.news

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel
@Inject constructor() : ViewModel() {
    val showWebView = mutableStateOf(false)
}