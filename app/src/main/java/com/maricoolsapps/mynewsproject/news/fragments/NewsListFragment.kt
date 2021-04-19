package com.maricoolsapps.mynewsproject.news.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.maricoolsapps.mynewsproject.R
import com.maricoolsapps.mynewsproject.databinding.FragmentNewsListBinding
import com.maricoolsapps.mynewsproject.news.adapter.TabAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsListFragment : Fragment(R.layout.fragment_news_list) {

    var _binding : FragmentNewsListBinding? = null
    val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewsListBinding.bind(view)
        val adapter = TabAdapter(
            requireActivity().supportFragmentManager
        )
        adapter.addFragment(HealthFragment(), "Health")
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
        adapter.addFragment(SportsFragment(), "Sports")

       binding.pager.adapter = adapter
        binding.apply {
            tabLayout.setupWithViewPager(pager, true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}