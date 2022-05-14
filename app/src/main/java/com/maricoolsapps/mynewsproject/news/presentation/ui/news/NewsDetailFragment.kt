package com.maricoolsapps.mynewsproject.news.presentation.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.maricoolsapps.mynewsproject.news.appcomponents.ApplicationClass
import com.maricoolsapps.mynewsproject.news.presentation.app_themes.AppTheme
import com.maricoolsapps.mynewsproject.news.presentation.composables.DEFAULT_NEWS_IMAGE
import com.maricoolsapps.mynewsproject.news.utils.loadPicture
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@ExperimentalMaterialApi
@AndroidEntryPoint
class NewsDetailFragment : Fragment() {

    private val viewModel: NewsDetailViewModel by viewModels()
    private val args: NewsDetailFragmentArgs by navArgs()

    @Inject
    lateinit var app: ApplicationClass

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val uri = args.news.url

        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme(
                    darkTheme = app.isDark.value
                ) {
                    val showWebView = viewModel.showWebView
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colors.surface),
                    ) {
                        args.news.title?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.h5,
                                fontSize = 30.sp,
                                fontWeight = FontWeight(500),
                                color = MaterialTheme.colors.onSurface,
                                modifier = Modifier.padding(12.dp),
                                textAlign = TextAlign.Start
                            )
                        }
                        val image =
                            args.news.photo?.let {
                                loadPicture(
                                    url = it,
                                    defaultImg = DEFAULT_NEWS_IMAGE
                                ).value
                            }
                        image?.let {
                            Image(
                                bitmap = it.asImageBitmap(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(225.dp)
                                    .padding(bottom = 3.dp),
                                contentScale = ContentScale.Crop,
                                contentDescription = "Image",
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 3.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            args.news.sourceName?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.body1,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(start = 5.dp),
                                    textAlign = TextAlign.Start
                                )
                            }
                            args.news.author?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.body1,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(end = 5.dp),
                                    textAlign = TextAlign.End
                                )
                            }
                        }
                        args.news.content?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.h5,
                                fontWeight = FontWeight(400),
                                color = MaterialTheme.colors.onSurface,
                                modifier = Modifier.padding(8.dp),
                                textAlign = TextAlign.Start
                            )
                        }
                        args.news.url?.let { uri ->
                            Text(
                                text = "Continue Reading",
                                style = MaterialTheme.typography.h5,
                                fontWeight = FontWeight(300),
                                color = Color.Blue,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .clickable(onClick = {
                                        showWebView.value = true
                                    }),
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                    uri?.let { ShowWebView(uri = it, isReady = showWebView.value) }
                }
            }
        }
    }
}

@Composable
fun ShowWebView(uri: String, isReady: Boolean) {
    if (isReady) {
        AndroidView(factory = {
            WebView(it).apply {
                webViewClient = WebViewClient()
                loadUrl(uri)
            }
        })
    }
}





