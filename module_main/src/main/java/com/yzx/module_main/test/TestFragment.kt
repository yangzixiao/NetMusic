package com.yzx.module_main.test

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yzx.lib_base.base.BaseFragment
import com.yzx.lib_base.base.BaseViewModel
import com.yzx.lib_base.widget.viewpager.BaseLazyLoadFragmentPagerAdapter
import com.yzx.module_main.databinding.FragmentDiscoverBinding

class TestFragment : BaseFragment<BaseViewModel>() {

    private lateinit var binding: FragmentDiscoverBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentDiscoverBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.apply {

            val fragmentPagerAdapter = BaseLazyLoadFragmentPagerAdapter(childFragmentManager)
            fragmentPagerAdapter.setData(
                listOf(DiscoverFragment(), DiscoverFragment(), DiscoverFragment()),
                listOf("111", "222", "333"))
            offscreenPageLimit = 3
            adapter = fragmentPagerAdapter
            binding.tabLayout.setupWithViewPager(this)
        }

    }

    override fun lazyLoad() {
        super.lazyLoad()
        Log.i("Mine", "TestlazyLoad: ")
    }
}