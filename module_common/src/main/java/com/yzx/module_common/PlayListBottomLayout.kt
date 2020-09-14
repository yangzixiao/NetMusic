package com.yzx.module_common

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.qmuiteam.qmui.nestedScroll.QMUIContinuousNestedBottomDelegateLayout
import com.qmuiteam.qmui.nestedScroll.QMUIContinuousNestedBottomRecyclerView
import com.yzx.lib_base.ext.*
import com.yzx.lib_base.utils.glide.GlideUtils
import com.yzx.lib_base.widget.recyclerview.ExtraLinearItemDecoration
import com.yzx.module_common.adpter.PlayListSongAdapter
import com.yzx.module_common.adpter.PlayListSubscriberAdapter
import com.yzx.module_common.databinding.LayoutPlayListFootBinding
import com.yzx.module_common.databinding.LayoutPlayListHeadBinding
import com.yzx.module_common.model.PlayListDetailResponse
import com.yzx.module_common.model.Playlist


class PlayListBottomLayout(context: Context, attrs: AttributeSet? = null, defaultAttr: Int = 0) :
    QMUIContinuousNestedBottomDelegateLayout(
        context, attrs, defaultAttr
    ) {


    companion object {
        private val menuIcons = mutableListOf(
            R.drawable.ic_comment, R.drawable.ic_share, R.drawable.ic_download,
            R.drawable.ic_muil_choose
        )
        private val menuTitles =
            mutableListOf(R.string.Comment, R.string.Share, R.string.Download, R.string.MuilChoose)
    }

    private lateinit var topViewBinding: LayoutPlayListHeadBinding
    private lateinit var recyclerView: QMUIContinuousNestedBottomRecyclerView
    override fun onCreateHeaderView(): View {
        topViewBinding = LayoutPlayListHeadBinding.inflate(LayoutInflater.from(context))

        for (index in 0..3) {
            topViewBinding.playListTab.apply {
                val newTab = newTab()
                newTab.setText(menuTitles[index])
                newTab.setIcon(menuIcons[index])
                addTab(newTab)
            }
        }
        return topViewBinding.root
    }

    override fun getHeaderStickyHeight(): Int {
        return context.getDefaultStatusAndToolbarHeight() + 50.dp().toInt()
    }


    override fun onCreateContentView(): View {
        recyclerView = QMUIContinuousNestedBottomRecyclerView(context)
        recyclerView.adapter = PlayListSongAdapter()
        recyclerView.layoutManager = LinearLayoutManager(context)
        return recyclerView
    }

    @SuppressLint("SetTextI18n")
    fun setData(playList: Playlist) {
        val songAdapter = recyclerView.adapter as PlayListSongAdapter
        songAdapter.addData(playList.tracks)
        if (playList.subscribers.isNullOrEmpty()) {
            return
        }

        val footBinding = LayoutPlayListFootBinding.inflate(LayoutInflater.from(context))
        footBinding.apply {
            recyclerView.apply {
                adapter = PlayListSubscriberAdapter(playList.subscribers.toMutableList())
                addItemDecoration(ExtraLinearItemDecoration(10.dp().toInt()))
            }
            tvCollectCount.text = "${playList.subscribedCount}人收藏"
        }
        songAdapter.addFooterView(footBinding.root)
    }

    @SuppressLint("SetTextI18n")
    fun updateHeadView(playListDetailData: PlayListDetailResponse) {
        val playlist = playListDetailData.playlist
        topViewBinding.apply {

            tvPlayListName.text = playlist.name
            val creator = playlist.creator
            tvCreatorName.apply {
                text = creator.nickname
                setOnClickListener {

                }
            }
            GlideUtils.loadImg(creator.avatarUrl, ivCreatorHead)
            tvPlayListDes.apply {
                val description = playlist.description
                text = if (TextUtils.isEmpty(description)) getString(
                    R.string.EditInfo
                ) else description
                setOnClickListener {

                }
            }
            tvCount.text = "(${playlist.trackCount})首"
            playListTab.getTabAt(0)!!.text = playlist.commentCount.defaultDes(R.string.Comment)
            playListTab.getTabAt(1)!!.text = playlist.shareCount.defaultDes(R.string.Share)
            val subscribedCount = playlist.subscribedCount
            if (subscribedCount > 0) {
                tvCollect.visible()
                tvCollect.text = "${context.getText(R.string.Collect)}(${subscribedCount}首)"
            } else {
                tvCollect.gone()
            }
        }
    }

    fun updateCover(coverImgUrl: String) {
        topViewBinding.apply {
            GlideUtils.loadImg(coverImgUrl, ivPoster)
            GlideUtils.loadDrawable(coverImgUrl, ivBackground, 100, 8)
            GlideUtils.loadDrawable(coverImgUrl, ivForeground, 100, 8)

        }
    }

    fun updateForeground(alpha: Float) {
        topViewBinding.ivForeground.alpha = alpha
    }


}