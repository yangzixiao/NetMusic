package com.yzx.module_main

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.appbar.AppBarLayout
import com.yzx.lib_base.BaseBottomSongInfoActivity
import com.yzx.lib_base.arouter.ARouterNavUtils
import com.yzx.lib_base.arouter.ARouterPath
import com.yzx.lib_base.ext.getDefaultStatusHeight
import com.yzx.module_main.adapter.MainFragmentPagerAdapter
import com.yzx.module_main.databinding.ActivityMainBinding
import com.yzx.module_main.test.DiscoverFragment
import com.yzx.module_main.widget.MainTitleView
import com.yzx.lib_magic_indicator.ViewPagerHelper
import com.yzx.lib_magic_indicator.buildins.commonnavigator.CommonNavigator
import com.yzx.lib_magic_indicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import com.yzx.lib_magic_indicator.buildins.commonnavigator.abs.IPagerIndicator
import com.yzx.lib_magic_indicator.buildins.commonnavigator.abs.IPagerTitleView
import com.yzx.lib_magic_indicator.buildins.commonnavigator.indicators.LinePagerIndicator


/**
 * MainActivity
 */
@Route(path = ARouterPath.MAIN)
class MainActivity : BaseBottomSongInfoActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun getActivityEnterAnim(): Int {
        return R.anim.slide_in_scale
    }

    override fun getActivityExitAnim(): Int {
        return R.anim.slide_out_scale
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setBottomActivityContentView(mainBinding.root)
        mainBinding.ivStatusView.layoutParams =
            AppBarLayout.LayoutParams(-1, getDefaultStatusHeight())

        setTransparentStatus()
        setupViewPagerAndIndicator()
        setStatusDarkFont()
        mainBinding.viewPager.currentItem = 1
        mainBinding.magicIndicator.onPageSelected(1)
    }

    private fun setupViewPagerAndIndicator() {
        val titles = listOf("我的", "发现", "云村", "视频")

        val mainFragmentPagerAdapter = MainFragmentPagerAdapter(supportFragmentManager)
        mainBinding.viewPager.apply {
            offscreenPageLimit = titles.size
            adapter = mainFragmentPagerAdapter
        }

        val fragment = ARouterNavUtils.getFragment(ARouterPath.FRAGMENT_MINE)
        mainFragmentPagerAdapter.fragments =
            listOf(
                mainFragmentPagerAdapter.getRegisteredFragment(0) ?: fragment,
                mainFragmentPagerAdapter.getRegisteredFragment(1) ?: DiscoverFragment(),
                mainFragmentPagerAdapter.getRegisteredFragment(2) ?: DiscoverFragment(),
                mainFragmentPagerAdapter.getRegisteredFragment(3) ?: DiscoverFragment()
            )
        mainFragmentPagerAdapter.notifyDataSetChanged()


        val commonNavigator = CommonNavigator(this)
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val titleView = MainTitleView(context)
                titleView.normalColor = Color.GRAY
                titleView.selectedColor = Color.BLACK
                titleView.textSize = 18f
                titleView.text = titles[index]
                titleView.setOnClickListener {
                    mainBinding.viewPager.currentItem = index
                }
                return titleView
            }

            override fun getCount(): Int {
                return titles.size
            }

            override fun getIndicator(context: Context?): IPagerIndicator {
                val linePagerIndicator = LinePagerIndicator(context)
                linePagerIndicator.lineHeight = 0f
                return linePagerIndicator
            }

        }
        mainBinding.magicIndicator.navigator = commonNavigator
        ViewPagerHelper.bind(mainBinding.magicIndicator, mainBinding.viewPager)
    }

}
