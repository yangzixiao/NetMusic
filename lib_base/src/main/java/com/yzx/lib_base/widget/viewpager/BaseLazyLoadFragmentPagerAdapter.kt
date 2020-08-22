package com.yzx.lib_base.widget.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

open class BaseLazyLoadFragmentPagerAdapter(fragmentManager: FragmentManager,
    behavior: Int = FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) :
    FragmentPagerAdapter(fragmentManager, behavior) {
    private var fragments: List<Fragment> = listOf()
    private var titles: List<String> = listOf()

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (titles.isNullOrEmpty()) null else titles[position]
    }

    fun setData(newFragments: List<Fragment>, newTitles: List<String>) {
        if (newFragments.isNullOrEmpty()) {
            return
        }
        this.fragments = newFragments
        titles = newTitles
        notifyDataSetChanged()
    }
}