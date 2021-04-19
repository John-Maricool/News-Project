package com.maricoolsapps.mynewsproject.news.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter

@Suppress("DEPRECATION")
class TabAdapter(fragment: FragmentManager): FragmentStatePagerAdapter(fragment) {

    private val theFragments: MutableList<Fragment> = mutableListOf()
    private val theFragmentTitle: MutableList<String> = mutableListOf()

    override fun getItem(position: Int): Fragment {
        return theFragments.get(position)
    }

    public fun addFragment(fragment: Fragment, title: String){
        theFragments.add(fragment)
        theFragmentTitle.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return theFragmentTitle.get(position)
    }

    override fun getCount(): Int {
        return theFragments.size
    }


}