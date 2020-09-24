package com.yzx.module_main.adapter

import android.util.SparseArray
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


/**
 * @author yzx
 * @date 2020/4/23
 * Description
 */
class MainFragmentPagerAdapter(
    fragmentManager: FragmentManager,
    behavior: Int = FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) :
    FragmentPagerAdapter(fragmentManager, behavior) {

    private var registeredFragments = SparseArray<Fragment>()
    var fragments: List<Fragment>? = null

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as Fragment
        registeredFragments.put(position, fragment)
        return fragment
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        registeredFragments.remove(position)
        super.destroyItem(container, position, `object`)
    }

    fun getRegisteredFragment(position: Int): Fragment? {
        return registeredFragments[position]
    }

    override fun getItem(position: Int): Fragment {
        return fragments!![position]
    }

    override fun getCount(): Int {
        return if (fragments.isNullOrEmpty()) 0 else fragments!!.size
    }
}