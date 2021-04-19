package com.maricoolsapps.mynewsproject.news.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.maricoolsapps.mynewsproject.R
import com.maricoolsapps.mynewsproject.databinding.FragmentNewsDetailBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewsDetailFragment : Fragment(R.layout.fragment_news_detail) {

    var _binding : FragmentNewsDetailBinding? = null
    val binding get() = _binding!!

    private val args by navArgs<NewsDetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewsDetailBinding.bind(view)

        binding.apply {
            val title = args.news.title
            val content = args.news.content
            val date = args.news.publishedAt
            val url = args.news.url
            val author = args.news.author
            val image = args.news.urlToImage

            Glide.with(requireActivity())
                .load(image)
                .centerCrop()
                .into(newsImage)
            titleText.text = title
            val uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            contentText.text = content
            dateText.text = "This news was published on $date by $author"
            urlText.text = "The source is $url"
            urlText.setOnClickListener{
                context?.startActivity(intent)
            }
            urlText.paint.isUnderlineText = true

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}