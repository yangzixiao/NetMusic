package com.yzx.module_common.playlistdetail

import android.os.Bundle
import android.view.Menu
import android.view.ViewTreeObserver
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.qmuiteam.qmui.kotlin.matchParent
import com.qmuiteam.qmui.nestedScroll.QMUIContinuousNestedBottomAreaBehavior
import com.qmuiteam.qmui.nestedScroll.QMUIContinuousNestedScrollLayout
import com.yzx.lib_base.arouter.ARouterPath
import com.yzx.lib_base.arouter.ArouterNavKey
import com.yzx.lib_base.base.BaseActivity
import com.yzx.lib_base.ext.*
import com.yzx.module_common.PlayListBottomLayout
import com.yzx.module_common.R
import com.yzx.module_common.databinding.ActivityPlayListDetailBinding
import com.yzx.module_common.model.PlayListDetailResponse
import org.koin.androidx.viewmodel.ext.android.viewModel


@Route(path = ARouterPath.COMMON_PLAYLIST_DETAIL)
class PlayListDetailActivity : BaseActivity(), ViewTreeObserver.OnGlobalLayoutListener {

    private var isRequestFinished = false
    private lateinit var playListName: String
    private var playListId: Long = 0
    private val playListViewModel: PlayListDetailViewModel by viewModel()
    private lateinit var mBottomDelegateLayout: PlayListBottomLayout
    private lateinit var binding: ActivityPlayListDetailBinding
    private var headViewHeight = 0
    private lateinit var viewTreeObserver: ViewTreeObserver

    init {
        e("init")
    }

    private var toolbarHeight: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        e("onCreate")
        binding = ActivityPlayListDetailBinding.inflate(layoutInflater)

        toolbarHeight = getDefaultStatusAndToolbarHeight()

        setContentView(binding.root)
        setTransparentStatus()
        setStatusWhiteFont()
        initView()
        doPrepareJob()
//        initViewModel(playListViewModel)
        playListViewModel.playListDetailLiveData.observe(this, {
            setupData(it)
        })
        if (playListId == 0L) {
            toast("歌单获取异常")
            return
        }
        playListViewModel.getPlayListDetail(playListId)

    }

    private fun doPrepareJob() {
        var poserUrl =
            intent.getStringExtra(ArouterNavKey.KEY_POSTER_URL)
        if (poserUrl.isNullOrEmpty()) {
            poserUrl = "http://p2.music.126.net/HS7lAAaxrSIJx8et_nzwqw==/19065531626222037.jpg"
        }
        playListId = intent.getLongExtra(ArouterNavKey.KEY_PLAYLIST_ID, 2181606783)
        mBottomDelegateLayout.updateCover(poserUrl)
    }

    private fun setupData(playListDetailData: PlayListDetailResponse) {
        isRequestFinished = true
        playListName = playListDetailData.playlist.name
        mBottomDelegateLayout.apply {
            addData(playListDetailData.playlist.tracks)
            updateHeadView(playListDetailData)
        }
    }

    private fun initNestScrollView() {
        binding.apply {
            mBottomDelegateLayout =
                PlayListBottomLayout(this@PlayListDetailActivity)
            val recyclerViewLp = CoordinatorLayout.LayoutParams(
                matchParent, matchParent
            )
            recyclerViewLp.behavior = QMUIContinuousNestedBottomAreaBehavior()
            qMUINestScrollView.setBottomAreaView(mBottomDelegateLayout, recyclerViewLp)
            qMUINestScrollView.addOnScrollListener(object :
                QMUIContinuousNestedScrollLayout.OnScrollListener {
                override fun onScroll(scrollLayout: QMUIContinuousNestedScrollLayout?,
                                      topCurrent: Int,
                                      topRange: Int,
                                      offsetCurrent: Int,
                                      offsetRange: Int,
                                      bottomCurrent: Int,
                                      bottomRange: Int) {

                    updateToolbarTitle(bottomCurrent)
                    updateHeadViewBackground(bottomCurrent)

                }

                override fun onScrollStateChange(scrollLayout: QMUIContinuousNestedScrollLayout?,
                                                 newScrollState: Int,
                                                 fromTopBehavior: Boolean) {
                }
            })
        }
    }

    /**
     * 根据滑动距离更新标题
     */
    private fun updateToolbarTitle(bottomCurrent: Int) {
        binding.layoutToolBar.tvTitle.text =
            if (bottomCurrent > toolbarHeight && isRequestFinished && playListName.isNotBlank()) playListName else simpleGetString(
                com.yzx.lib_base.R.string.PlayList
            )
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    private fun initView() {
        updateToolbarTitle(0)
        binding.layoutToolBar.toolbar.apply {
            val layoutParams = layoutParams
            layoutParams.height = toolbarHeight
            setPadding(0, getDefaultStatusHeight(), 0, 0)
            this.layoutParams = layoutParams
            inflateMenu(R.menu.menu_play_list_detail)
            overflowIcon = simpleGetDrawable(R.drawable.ic_elipsis)
            setMenuIconVisible(this)
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_search -> {
                        toast(R.string.Search)
                    }
                    R.id.menu_clear -> {

                    }
                }
                true
            }
            setNavigationOnClickListener {
                finish()
            }
        }
        initNestScrollView()
        viewTreeObserver = binding.root.viewTreeObserver
        viewTreeObserver.addOnGlobalLayoutListener(this)
    }


    private fun updateHeadViewBackground(bottomCurrent: Int) {
        if (headViewHeight == 0) {
            return
        }
        val alpha = bottomCurrent.toFloat() / headViewHeight.toFloat()
        mBottomDelegateLayout.updateForeground(alpha)
    }

    override fun onGlobalLayout() {
        if (headViewHeight == 0) {
            headViewHeight = mBottomDelegateLayout.headerView.measuredHeight - 50.dp()
                .toInt() - toolbarHeight
        }
        if (viewTreeObserver.isAlive) {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
        }
    }


}