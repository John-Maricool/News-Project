package com.maricoolsapps.mynewsproject.news.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.maricoolsapps.mynewsproject.R
import com.maricoolsapps.mynewsproject.databinding.FragmentBusinessBinding
import com.maricoolsapps.mynewsproject.news.NewsRecyclerAdapter
import com.maricoolsapps.mynewsproject.news.NewsRepository
import com.maricoolsapps.mynewsproject.news.models.Articles
import com.maricoolsapps.mynewsproject.news.models.news_model
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.news_list_recycler_view.*
import javax.inject.Inject

@AndroidEntryPoint
class LifestyleFragment : Fragment(R.layout.fragment_business),
    NewsRecyclerAdapter.onItemClickListener {

    private var _binding: FragmentBusinessBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var repository: NewsRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentBusinessBinding.bind(view)
        val adapter = NewsRecyclerAdapter(this)

        binding.apply {
            includeView.recyclerView.setHasFixedSize(true)
            includeView.recyclerView.layoutManager = LinearLayoutManager(activity)
            includeView.recyclerView.adapter = adapter
        }

        repository.getNews("lifestyle").observe(viewLifecycleOwner, Observer {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        })

        binding.includeView.swipeToRefresh.setOnRefreshListener {
            adapter.retry()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClick(photo: Articles) {
        val action = NewsListFragmentDirections.actionNewsListFragmentToNewsDetailFragment(photo)
        findNavController().navigate(action)
    }

}