package com.yzx.module_common.playlistdetail

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.appbar.AppBarLayout
import com.yzx.lib_base.arouter.ARouterPath
import com.yzx.lib_base.arouter.ArouterNavKey
import com.yzx.lib_base.base.BaseActivity
import com.yzx.lib_base.ext.getToolBarSize
import com.yzx.lib_base.ext.toast
import com.yzx.lib_base.utils.StatusUtils
import com.yzx.lib_base.utils.glide.GlideUtils
import com.yzx.module_common.adpter.SongAdapter
import com.yzx.module_common.databinding.ActivityPlayListDetailBinding
import com.yzx.module_common.model.PlayListDetailResponse
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs


@Route(path = ARouterPath.COMMON_PLAYLIST_DETAIL)
class PlayListDetailActivity : BaseActivity() {

    private var isRequestFinished = false
    private lateinit var toolbarTitle: String
    private var playListId: Long = 0
    private val playListViewModel: PlayListDetailViewModel by viewModel()
    private lateinit var binding: ActivityPlayListDetailBinding
    private val songAdapter by lazy {
        SongAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayListDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTransparentStatus()
        setStatusWhiteFont()
        doPrepareJob()
        initView()
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
        val poserUrl =
            intent.getStringExtra(ArouterNavKey.KEY_POSTER_URL)
        playListId = intent.getLongExtra(ArouterNavKey.KEY_PLAYLIST_ID, 0)
        GlideUtils.loadDrawable(poserUrl, binding.ivBackground, 100, 10)
        GlideUtils.loadImg(poserUrl, binding.ivPoster)
    }

    private fun setupData(playListDetailData: PlayListDetailResponse) {
        isRequestFinished = true
        toolbarTitle = playListDetailData.playlist.name
        songAdapter.addData(playListDetailData.playlist.tracks)
        binding.apply {

        }
    }

    private fun initView() {
        binding.apply {

            val stateBarHeight = StatusUtils.getStateBarHeight(this@PlayListDetailActivity)
            val layoutParams = layoutToolBar.toolbar.layoutParams
            layoutParams.height = getToolBarSize() + stateBarHeight
            layoutToolBar.toolbar.setPadding(0, stateBarHeight, 0, 0)
            layoutToolBar.toolbar.layoutParams = layoutParams
            layoutToolBar.toolbar.setNavigationOnClickListener {
                finish()
            }
            appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                val totalScrollRange = appBarLayout.totalScrollRange
                val movePercent = abs(verticalOffset).toDouble() / totalScrollRange.toDouble()
                ivBackground.translationY = verticalOffset.toFloat()
                if (isRequestFinished) {
                    layoutToolBar.tvTitle.text =
                        if (movePercent < 0.3) getString(com.yzx.lib_base.R.string.PlayList) else toolbarTitle

//                    val drawable = ivBackground.drawable
//                    if (drawable != null) {
//                        drawable.alpha = movePercent.toInt()
//                        layoutToolBar.toolbar.background = drawable
//                    }
                } else {
                    layoutToolBar.tvTitle.text = getString(com.yzx.lib_base.R.string.PlayList)
                }
            })

            recyclerView.apply {
                songAdapter.data
                adapter = songAdapter
                layoutManager = LinearLayoutManager(this@PlayListDetailActivity)
            }
        }
    }

    private fun getSongData(): MutableList<String> {
        val songs = mutableListOf<String>()

        for (index in 1..2) {
            songs.add(index.toString())
        }
        return songs
    }
}