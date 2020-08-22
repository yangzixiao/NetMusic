package com.yzx.module_main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


/**
 * @author yzx
 * @date 2020/4/23
 * Description
 */
class MainFragmentPagerAdapter(fragmentManager: FragmentManager,
    behavior: Int = FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) :
    FragmentPagerAdapter(fragmentManager, behavior) {
    var fragments: List<Fragment>? = null
    override fun getItem(position: Int): Fragment {
        return fragments!![position]
    }

    override fun getCount(): Int {
        return if (fragments.isNullOrEmpty()) 0 else fragments!!.size
    }
}