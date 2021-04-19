package com.maricoolsapps.mynewsproject.news.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.maricoolsapps.mynewsproject.R
import com.maricoolsapps.mynewsproject.databinding.FragmentNewsListBinding
import com.maricoolsapps.mynewsproject.news.NewsRecyclerAdapter
import com.maricoolsapps.mynewsproject.news.NewsViewModel
import com.maricoolsapps.mynewsproject.news.adapter.TabAdapter
import com.maricoolsapps.mynewsproject.news.models.Articles
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsListFragment : Fragment(R.layout.fragment_news_list),
    NewsRecyclerAdapter.onItemClickListener {

    var _binding : FragmentNewsListBinding? = null
    val binding get() = _binding!!

    private val viewModel: NewsViewModel by viewModels<NewsViewModel>()

    lateinit var adapter: NewsRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewsListBinding.bind(view)
       /* val adapter = TabAdapter(
            requireActivity().supportFragmentManager
        )*/

        binding.apply {
            tabLayout.addTab(tabLayout.newTab().setText("Health"));
            tabLayout.addTab(tabLayout.newTab().setText("Technology"));
            tabLayout.addTab(tabLayout.newTab().setText("Politics"));
            tabLayout.addTab(tabLayout.newTab().setText("Business"));
            tabLayout.addTab(tabLayout.newTab().setText("Lifestyle"));
            tabLayout.addTab(tabLayout.newTab().setText("Football"));
            tabLayout.addTab(tabLayout.newTab().setText("Music"));
            tabLayout.addTab(tabLayout.newTab().setText("Bitcoin"));
            tabLayout.addTab(tabLayout.newTab().setText("Nigeria"));
            tabLayout.addTab(tabLayout.newTab().setText("Entertainment"));
            tabLayout.addTab(tabLayout.newTab().setText("Weather"));
            tabLayout.addTab(tabLayout.newTab().setText("Science"));
            tabLayout.addTab(tabLayout.newTab().setText("Sports"));
        }

        /*adapter.addFragment(HealthFragment(), "Health")
        adapter.addFragment(TechnologyFragment(), "Technology")
        adapter.addFragment(PoliticsFragment(), "Politics")
        adapter.addFragment(BusinessFragment(), "Business")
        adapter.addFragment(LifestyleFragment(), "Lifestyle")
        adapter.addFragment(FootballFragment(), "Football")
        adapter.addFragment(MusicFragment(), "Music")
        adapter.addFragment(BitcoinFragment(), "Bitcoin")
        adapter.addFragment(NigeriaFragment(), "Nigeria")
        adapter.addFragment(EntertainmentFragment(), "Entertainment")
        adapter.addFragment(WeatherFragment(), "Weather")
        adapter.addFragment(ScienceFragment(), "Science")
        adapter.addFragment(SportsFragment(), "Sports")*/

      /* binding.pager.adapter = adapter
        binding.apply {
            tabLayout.setupWithViewPager(pager, true)
        }*/
         adapter = NewsRecyclerAdapter(this)

        binding.apply {
            includeView.recyclerView.setHasFixedSize(true)
            includeView.recyclerView.layoutManager = LinearLayoutManager(activity)
            includeView.recyclerView.adapter = adapter
        }
        viewModel.news.observe(viewLifecycleOwner, Observer {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        })

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                includeView.recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error

                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    includeView.recyclerView.isVisible = false
                    textViewEmpty.isVisible = true
                } else {
                    textViewEmpty.isVisible = false
                }
            }
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {
                val text = tab?.text.toString()
                viewModel.searchNews(text)
                adapter.retry()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                adapter.retry()
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val text = tab?.text.toString().toLowerCase()
                viewModel.searchNews(text)
                adapter.refresh()
            }
        })

        binding.includeView.swipeToRefresh.setOnRefreshListener {
            adapter.refresh()
            binding.includeView.swipeToRefresh.isRefreshing = false
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