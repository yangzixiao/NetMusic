package com.yzx.module_mine.ui


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.multitype.adapter.MultiTypeAdapter
import com.multitype.adapter.binder.MultiTypeBinder
import com.multitype.adapter.createMultiTypeAdapter
import com.noober.background.drawable.DrawableCreator
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.yzx.lib_base.arouter.ARouterNavUtils
import com.yzx.lib_base.arouter.ARouterPath
import com.yzx.lib_base.arouter.ArouterNavKey
import com.yzx.lib_base.base.BaseFragment
import com.yzx.lib_base.ext.getColor
import com.yzx.lib_base.ext.getDefaultStatusAndToolbarHeight
import com.yzx.lib_base.ext.gone
import com.yzx.lib_base.ext.visible
import com.yzx.lib_base.manager.UserInfoManager.userInfoLiveData
import com.yzx.lib_base.model.UserDataBean
import com.yzx.lib_base.utils.ColorUtils
import com.yzx.lib_base.utils.DenistyUtils.dip2px
import com.yzx.lib_base.utils.glide.GlideUtils
import com.yzx.module_mine.R
import com.yzx.module_mine.adapter.HorizontalPlayListBinder
import com.yzx.module_mine.adapter.ItemPlayListBinder
import com.yzx.module_mine.adapter.MineHeadMenuAdapter
import com.yzx.module_mine.databinding.FragmentMineBinding
import com.yzx.module_mine.model.MineHeadMenuBean
import com.yzx.module_mine.model.MinePagerData
import com.yzx.module_mine.viewmodel.MineViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
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

    //第一次更新不需要调研
    private var needRefresh = false
    private lateinit var mineBinding: FragmentMineBinding
    private lateinit var mineAdapter: MultiTypeAdapter
    private lateinit var userDataBean: UserDataBean
    private val binders = arrayListOf<MultiTypeBinder<*>>()
    private val layoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(context)
    }

    private val viewModel: MineViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.mineBinding = FragmentMineBinding.inflate(inflater)
        initView()
        return mineBinding.root
    }

    private fun initView() {
        mineAdapter =
            createMultiTypeAdapter(mineBinding.recyclerView, layoutManager)
        mineBinding.apply {
            initToolBar()
            initHeadMenu()
            setAppBarLayoutScrollListener()
            setupSwipeRefreshLayout()
            bindingRecyclerViewAndTabLayout(recyclerView, tabLayout)
        }
        userInfoLiveData.observe(viewLifecycleOwner, {
            userDataBean = it
            onUserStateChanged()
            if (needRefresh) {
                mineBinding.smartRefreshLayout.autoRefresh()
            }
            needRefresh = true
        })

        viewModel.minePagerLiveData.observe(viewLifecycleOwner, {
            setupData(it)
            updateMyFavoriteMusic(it)

            mineBinding.smartRefreshLayout.finishRefresh()
        })
    }

    @SuppressLint("SetTextI18n")
    private fun updateMyFavoriteMusic(minePagerData: MinePagerData) {
        mineBinding.layoutMineHead.apply {
            if (userDataBean.isLoggedIn) {
                val playlistBean = minePagerData.playlist!![0]
                GlideUtils.loadImg(playlistBean.coverImgUrl, ivMyFavorite)
                tvMyFavoriteDes.text = "${playlistBean.trackCount}首"
                llMyFavorite.setOnClickListener {
                    ARouterNavUtils.navToPlayListDetails(playlistBean.id, playlistBean.coverImgUrl)
                }
            }
        }
    }


    private fun bindingRecyclerViewAndTabLayout(recyclerView: RecyclerView, tabLayout: TabLayout) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val findFirstVisibleItemPosition =
                    layoutManager.findFirstVisibleItemPosition()
                tabLayout.setScrollPosition(findFirstVisibleItemPosition, 0f, true)

            }
        })
    }

    private fun FragmentMineBinding.initHeadMenu() {
        layoutMineHead.rvMineMenu.apply {
            layoutManager = GridLayoutManager(context, 4)
            val mineHeadMenuAdapter = MineHeadMenuAdapter(getHeadMenuData())
            adapter = mineHeadMenuAdapter
        }
    }

    private fun getHeadMenuData(): MutableList<MineHeadMenuBean> {
        return mutableListOf(
            MineHeadMenuBean(R.drawable.ic_local_music, R.string.LocalMusic),
            MineHeadMenuBean(R.drawable.ic_net_pan, R.string.NetPan),
            MineHeadMenuBean(R.drawable.ic_havd_buy, R.string.HadBuy),
            MineHeadMenuBean(R.drawable.ic_recent_play, R.string.RecentPlay),
            MineHeadMenuBean(R.drawable.ic_my_focus, R.string.MyFocus),
            MineHeadMenuBean(R.drawable.ic_collect, R.string.CollectionAndLike),
            MineHeadMenuBean(R.drawable.ic_my_radio, R.string.MyRadio),
            MineHeadMenuBean(R.drawable.ic_add, R.string.MusicApp),
        )
    }

    private fun updateTabLayout() {
        val tabTitles = if (userDataBean.isLoggedIn) arrayListOf(
            R.string.CreatePlayList,
            R.string.CollectPlayList
        ) else arrayListOf(R.string.RecommondPlayList)
        val tabLayout = mineBinding.tabLayout
        tabLayout.removeAllTabs()
        tabTitles.forEachIndexed { index, title ->
            val newTab = tabLayout.newTab()
            newTab.setText(title)
            newTab.view.setOnClickListener {
                mineBinding.appBarLayout.setExpanded(false)
                layoutManager.scrollToPositionWithOffset(index, 0)
            }
            tabLayout.addTab(newTab)
        }

    }

    /**
     * 懒加载
     */
    override fun lazyLoad() {
        super.lazyLoad()
        mineBinding.smartRefreshLayout.autoRefresh()
    }

    /**
     * 用户状态改变
     */
    private fun onUserStateChanged() {
        setupUserInfo()
        updateOnNotLogin()
        updateTabLayout()
    }

    private fun updateOnNotLogin() {
        if (!userDataBean.isLoggedIn) {
            mineBinding.layoutMineHead.apply {
                GlideUtils.simpleLoadImg(null, ivMyFavorite)
                tvMyFavoriteDes.text = "0首"
            }
        }
    }

    private fun getMinePagerData() {
        if (userDataBean.isLoggedIn) {
            viewModel.getMinePagerData(userDataBean.uid.toString())
        } else {
            viewModel.getMinePagerData()
        }
    }

    private fun setupData(minePagerData: MinePagerData) {
        binders.clear()
        if (minePagerData.recommendPlaylist == null) {
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
                add(
                    HorizontalPlayListBinder(
                        "创建歌单(${createPlayListBinders.size}个)", createPlayListBinders
                    )
                )
                add(
                    HorizontalPlayListBinder(
                        "收藏歌单(${collectionPlayListBinders.size}个)",
                        collectionPlayListBinders
                    )
                )
            }
        } else {
            binders.apply {
                add(HorizontalPlayListBinder("推荐歌单", minePagerData.recommendPlaylist!!.map {
                    ItemPlayListBinder(it)
                }))
            }
        }
        mineAdapter.notifyAdapterChanged(binders)
    }


    /**
     * 设置SmartRefreshLayout
     */
    private fun FragmentMineBinding.setupSwipeRefreshLayout() {
        val materialHeader = MaterialHeader(context)
        materialHeader.setColorSchemeResources(R.color.colorRed)
        smartRefreshLayout.setRefreshHeader(materialHeader as RefreshHeader)
        smartRefreshLayout.setOnRefreshListener {
            getMinePagerData()
        }
    }


    /**
     * 设置用户信息
     */
    private fun setupUserInfo() {
        val loggedIn = userDataBean.isLoggedIn
        mineBinding.layoutMineHead.apply {
            if (loggedIn) llUserInfo.visible() else llUserInfo.gone()
            if (loggedIn) blLogin.gone() else blLogin.visible()

            if (!loggedIn) {
                blLogin.setOnClickListener {
                    ARouterNavUtils.getPostcard(ARouterPath.LOGIN)
                        .withBoolean(ArouterNavKey.KEY_IS_FROM_Login_GUIDE, false).navigation()
                }
            } else {
                tvNickName.text = userDataBean.nickName
                tvLevel.text = "Lv9"
                GlideUtils.loadBitmap(
                    userDataBean.backgroundUrl, R.drawable.cbh, mineBinding.ivBackground,
                    true
                ) { _, color ->
                    setupBackground(color)
                }
            }
            val headResource = if (loggedIn) userDataBean.avatarUrl else R.color.colorImg
            GlideUtils.loadImg(
                headResource, GlideUtils.TYPE_HEAD, ivHead
            )
        }
    }

    private fun setupBackground(color: Int) {
        //处理获取的颜色可能出现透明度为0的情况
        val handledColor = ColorUtils.getColorByAlpha(color, 255)
        mineBinding.apply {
            ivBackground.setImageDrawable(
                DrawableCreator.Builder().setGradientAngle(90)
                    .setGradientColor(
                        getColor(R.color.colorTransparent),
                        ColorUtils.getColorByAlpha(handledColor, 0.5f)
                    )
                    .build()
            )
        }
    }

    private fun FragmentMineBinding.setAppBarLayoutScrollListener() {

        appBarLayout.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                //计算appBarLayout滑动比重
                val totalScrollRange = appBarLayout.totalScrollRange
                val movePercent = abs(verticalOffset).toDouble() / totalScrollRange.toDouble()

                toolbar.alpha =
                    (abs(verticalOffset).toDouble() / dip2px(context, 140f).toDouble()).toFloat()
                ivBackground.translationY = verticalOffset.toFloat()
                tabLayout.setBackgroundResource(if (movePercent >= 1) R.color.colorWhite else R.color.colorTransparent)
            })

    }

    /**
     * 初始化toolbar
     */
    private fun FragmentMineBinding.initToolBar() {
        toolbar.alpha = 0f
        val layoutParams = toolbar.layoutParams
        layoutParams.height =
            requireContext().getDefaultStatusAndToolbarHeight()
        toolbar.layoutParams = layoutParams
    }
}