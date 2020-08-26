package com.yzx.module_mine.ui

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager

import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout.LayoutParams
import com.google.android.material.appbar.CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN
import com.multitype.adapter.MultiTypeAdapter
import com.multitype.adapter.binder.MultiTypeBinder
import com.multitype.adapter.createMultiTypeAdapter
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.yzx.lib_base.ARouter.ARouterNavUtils

import com.yzx.lib_base.ARouter.ARouterPath
import com.yzx.lib_base.base.BaseFragment
import com.yzx.lib_base.manager.UserInfoManager
import com.yzx.lib_base.manager.UserInfoManager.userInfoLiveData
import com.yzx.lib_base.model.UserDataBean
import com.yzx.lib_base.utils.ColorUtils
import com.yzx.lib_base.utils.DenistyUtils.dip2px
import com.yzx.lib_base.utils.glide.GlideUtils
import com.yzx.lib_base.utils.StatusUtils

import com.yzx.module_mine.viewmodel.MineViewModel
import com.yzx.module_mine.R
import com.yzx.module_mine.adapter.ItemPlayListBinder
import com.yzx.module_mine.adapter.MyMusicBinder
import com.yzx.module_mine.adapter.MyMusicItemBinder
import com.yzx.module_mine.adapter.PlayListBinder
import com.yzx.module_mine.databinding.FragmentMineBinding


import com.yzx.module_mine.model.MinePagerData
import com.yzx.module_mine.model.MyMusicBean
import kotlinx.coroutines.isActive
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

import kotlin.math.abs

/**
 * @author yzx
 * @date 2020/4/1
 * Description
 */
@Route(path = ARouterPath.FRAGMENT_MINE)
class MineFragment : BaseFragment() {

    companion object {
        const val TAG = "MineFragment"
    }

    private lateinit var mineBinding: FragmentMineBinding
    private var mineAdapter: MultiTypeAdapter? = null
    private var keyColor: Int = 0x00000000

    val viewModel:MineViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.mineBinding = FragmentMineBinding.inflate(inflater)
        return mineBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupSwipeRefreshLayout()
        mineBinding.iv1.setOnClickListener {
            UserInfoManager.reset()
        }
        viewModel.minePagerLiveData.observe(viewLifecycleOwner, {
            setupData(it)
        })

    }

    /**
     * 懒加载
     */
    override fun lazyLoad() {
        super.lazyLoad()
        userInfoLiveData.observe(viewLifecycleOwner, {
            onUserStateChanged(it)
        })
    }

    /**
     * 用户状态改变
     */
    private fun onUserStateChanged(userDataBean: UserDataBean) {
        setupUserInfo(userDataBean)
        mineBinding.smartRefreshLayout.apply {
            setOnRefreshListener {
                if (userDataBean.isLoggedIn) {
                    viewModel.getMinePagerData(userDataBean.uid.toString())
                } else {
                    viewModel.getMinePagerData()
                }
            }
            autoRefresh()
        }
    }

//    override fun onLoadingStateChanged(loadingState: Boolean) {
//        if (!loadingState) {
//            mineBinding.smartRefreshLayout.finishRefresh()
//        }
//    }

    private fun setupData(minePagerData: MinePagerData) {
        if (mineAdapter == null) {
            mineAdapter =
                createMultiTypeAdapter(mineBinding.recyclerView, LinearLayoutManager(context))
        }

        val myMusicBeans =
            listOf(
                MyMusicBean(R.drawable.ic_like, "我喜爱的音乐", "心动模式", keyColor = keyColor),
                MyMusicBean(R.drawable.ic_personal_fm, "私人FM", "来这里找好歌", keyColor = keyColor),
                MyMusicBean(R.drawable.ccx, "推歌精选", "云贝助力好歌", keyColor = keyColor),
                MyMusicBean(R.drawable.ic_classical, "古典专区", "专业古典大全", keyColor = keyColor)
            ).map {
                MyMusicItemBinder(it)
            }
        val myMusicBinder = MyMusicBinder(listOf("我的音乐"), myMusicBeans)
        myMusicBeans[1].myMusicBean.background = minePagerData.personalFM!!.album.picUrl

        val binders = arrayListOf<MultiTypeBinder<*>>()
        if (minePagerData.recommendPlaylist == null) {
            myMusicBeans[0].myMusicBean.background = minePagerData.playlist!![0].coverImgUrl

            val playlist = minePagerData.playlist
            val createPlayListBinders = arrayListOf<ItemPlayListBinder>()
            val collectionPlayListBinders = arrayListOf<ItemPlayListBinder>()
            playlist!!.filter { it != playlist[0] }.forEach {

                if (it.creator.userId == userInfoLiveData.value!!.uid) {
                    createPlayListBinders.add(ItemPlayListBinder(it))
                } else {
                    collectionPlayListBinders.add(ItemPlayListBinder(it))
                }
            }

            binders.apply {
                add(myMusicBinder)
                add(PlayListBinder(listOf("最近播放"), listOf(ItemPlayListBinder(playlist[0]))))
                add(
                    PlayListBinder(
                        listOf("创建歌单", "收藏歌单"), createPlayListBinders,
                        collectionPlayListBinders
                    )
                )
            }
        } else {
            binders.apply {
                add(myMusicBinder)
                add(PlayListBinder(listOf("推荐歌单"), minePagerData.recommendPlaylist!!.map {
                    ItemPlayListBinder(it)
                }))
            }
        }

        mineAdapter!!.notifyAdapterChanged(binders)
    }


    private fun setupSwipeRefreshLayout() {
        val materialHeader = MaterialHeader(context)
        materialHeader.setColorSchemeResources(R.color.colorRed)
        val smartRefreshLayout = mineBinding.smartRefreshLayout
        smartRefreshLayout.setRefreshHeader(materialHeader as RefreshHeader)
    }


    /**
     * 设置用户信息
     */
    private fun setupUserInfo(userDataBean: UserDataBean) {

        val loggedIn = userDataBean.isLoggedIn

        mineBinding.layoutMineHead.apply {
            llUserInfo.visibility = if (loggedIn) View.VISIBLE else View.GONE
            tvLogin.visibility = if (loggedIn) View.GONE else View.VISIBLE
            if (!loggedIn) {
                tvLogin.setOnClickListener {
                    ARouterNavUtils.normalNav(ARouterPath.LOGIN)
                }
            }
            GlideUtils.loadImg(
                if (loggedIn) userDataBean.avatarUrl else R.color.colorImg,
                GlideUtils.TYPE_HEAD, ivHead
            )
        }

        GlideUtils.loadBitmap(
            userDataBean.backgroundUrl, R.drawable.cbh, mineBinding.ivBackground,
            true
        ) { bitmap, color ->
            setupBackground(bitmap, color)
        }
    }

    private fun setupBackground(bitmap: Bitmap, color: Int) {

        //处理获取的颜色可能出现透明度为0的情况
        val dealedColor = ColorUtils.getColorByAlpha(color, 255)
        this.keyColor = dealedColor
//        mineBinding.iv1.setBackgroundColor(dealedColor)
        mineBinding.apply {
            toolbar.setBackgroundColor(dealedColor)
            ivBackground.setMaskColor(ColorUtils.getColorByAlpha(dealedColor, 255 / 2))
        }
    }

    private fun setupToolbar() {

        mineBinding.apply {
            toolbar.alpha = 0f
            val layoutParams =
                LayoutParams(-1, StatusUtils.getStateBarHeight(context) + dip2px(context, 64f))
            layoutParams.collapseMode = COLLAPSE_MODE_PIN
            toolbar.layoutParams = layoutParams

            appBarLayout.addOnOffsetChangedListener(
                AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->

                    val totalScrollRange = appBarLayout.totalScrollRange
                    val movePercent = abs(verticalOffset).toDouble() / totalScrollRange.toDouble()
                    toolbar.alpha = movePercent.toFloat()
                    ivBackground.translationY = verticalOffset.toFloat()

                    if (movePercent >= 1) {
                        tabLayout.background = toolbar.background
                    } else {
                        tabLayout.setBackgroundResource(R.color.colorTransparent)
                    }
                })
        }
    }
}