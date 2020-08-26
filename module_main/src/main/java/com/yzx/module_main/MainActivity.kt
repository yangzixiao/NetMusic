package com.yzx.module_main

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.appbar.AppBarLayout
import com.yzx.lib_base.ARouter.ARouterNavUtils
import com.yzx.lib_base.ARouter.ARouterPath
import com.yzx.lib_base.BaseBottomSongInfoActivity
import com.yzx.lib_base.utils.StatusUtils
import com.yzx.module_main.adapter.MainFragmentPagerAdapter
import com.yzx.module_main.databinding.ActivityMainBinding
import com.yzx.module_main.test.DiscoverFragment
import com.yzx.module_main.test.TestFragment
import com.yzx.module_main.widget.MainTitleView

import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator


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


        mainBinding.ivStatusView.layoutParams =
            AppBarLayout.LayoutParams(-1, StatusUtils.getStateBarHeight(this))
        setContentView(mainBinding.root)
        setTransparentStatus()
        setupViewPagerAndIndicator()

        //
        setStatusWhiteFont()
        mainBinding.viewPager.currentItem = 0
        mainBinding.magicIndicator.onPageSelected(0)

//        setStatusDarkFont()
//        mainBinding.viewPager.currentItem = 1
//        mainBinding.magicIndicator.onPageSelected(1)
        setSongInfo("呜啦啦", "嘿嘿哈哈", "")
    }

    private fun setupViewPagerAndIndicator() {
        val titles = listOf("我的", "发现", "云村", "视频")

        val mainFragmentPagerAdapter = MainFragmentPagerAdapter(supportFragmentManager)
        mainBinding.viewPager.apply {
            offscreenPageLimit = titles.size
            adapter = mainFragmentPagerAdapter
        }

        val fragment = ARouterNavUtils.getFragment(ARouterPath.FRAGMENT_MINE)
        val elements = DiscoverFragment()
        val bundle = Bundle()
        bundle.putString("111", "1")
        elements.arguments = bundle
        mainFragmentPagerAdapter.fragments =
            listOf(fragment, elements,
                TestFragment(),
                TestFragment())
        mainFragmentPagerAdapter.notifyDataSetChanged()


        val commonNavigator = CommonNavigator(this)
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val mainTitleView = MainTitleView(context)

                mainTitleView.textSize = 18f
                mainTitleView.text = titles[index]
                mainTitleView.setOnClickListener {
                    mainBinding.viewPager.currentItem = index
                }
                return mainTitleView
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
        mainBinding.viewPager.addOnPageChangeListener(object :
            ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                if (position == 0) setStatusWhiteFont() else setStatusDarkFont()
//                mainBinding.titleContent.setBackgroundColor(resources.getColor(if (position == 0) R.color.colorTransparent else R.color.colorWhite))

                if (position == 0) {
                    val title2 = commonNavigator.getPagerTitleView(2) as TextView
                    val title3 = commonNavigator.getPagerTitleView(3) as TextView
                    title2.setTextColor(simpleGetColor(R.color.colorDarkWhite))
                    title3.setTextColor(simpleGetColor(R.color.colorDarkWhite))
                } else if (position == 1) {
                    val title2 = commonNavigator.getPagerTitleView(2) as TextView
                    val title3 = commonNavigator.getPagerTitleView(3) as TextView
                    title2.setTextColor(simpleGetColor(R.color.colorLightBlack))
                    title3.setTextColor(simpleGetColor(R.color.colorLightBlack))
                }
            }
        })
        ViewPagerHelper.bind(mainBinding.magicIndicator, mainBinding.viewPager)
    }

}
