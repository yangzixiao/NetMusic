package com.yzx.module_common

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.noober.background.drawable.DrawableCreator
import com.qmuiteam.qmui.nestedScroll.QMUIContinuousNestedBottomDelegateLayout
import com.qmuiteam.qmui.nestedScroll.QMUIContinuousNestedBottomRecyclerView
import com.yzx.lib_base.ext.*
import com.yzx.lib_base.manager.UserInfoManager
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
        return context.getDefaultStatusAndToolbarHeight() + 50f.dp.toInt()
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
                addItemDecoration(ExtraLinearItemDecoration(10f.dp.toInt()))
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
            val isSelf = creator.userId == UserInfoManager.userInfoLiveData.value?.uid
            tvCreatorName.text = creator.nickname
            GlideUtils.loadImg(creator.avatarUrl, ivCreatorHead)
            llCreator.setOnClickListener {

            }

            tvPlayListDes.apply {
                val description = playlist.description
                text = if (TextUtils.isEmpty(description)) simpleGetString(
                    if (isSelf) R.string.EditInfo else R.string.NoDesciption
                ) else description
                setOnClickListener {

                }
            }
            tvCount.text = "(${playlist.trackCount})首"
            playListTab.getTabAt(0)!!.text = playlist.commentCount.defaultDes(R.string.Comment)
            playListTab.getTabAt(1)!!.text = playlist.shareCount.defaultDes(R.string.Share)

            //收藏信息
            val subscribedCount = playlist.subscribedCount
            if (subscribedCount > 0) {
                tvCollect.visible()
                if (isSelf) {
                    tvCollect.text =
                        "${subscribedCount}${simpleGetString(R.string.People, R.string.Collect)}"
                    tvCollect.setBackgroundColor(Color.TRANSPARENT)
                    tvCollect.setTextColor(simpleGetColor(R.color.colorSubTitle))
                } else {
                    tvCollect.text = "${context.getText(R.string.Collect)}(${subscribedCount})"
                    tvCollect.background =
                        DrawableCreator.Builder().setSolidColor(getColor(R.color.colorRed))
                            .setCornersRadius(15f.dp).build()
                    tvCollect.setTextColor(Color.WHITE)
                }
            } else {
                tvCollect.gone()
            }
        }
    }

    fun updateCover(coverImgUrl: String) {
        topViewBinding.apply {
            GlideUtils.loadImg(coverImgUrl, ivPoster)
            val maskColor = simpleGetColor(R.color.colorGreyMask)
            ivForeground.setMaskColor(maskColor)
            ivBackground.setMaskColor(maskColor)
            GlideUtils.loadDrawable(coverImgUrl, ivBackground, 100, 8)
            GlideUtils.loadDrawable(coverImgUrl, ivForeground, 100, 8)

        }
    }

    fun updateForeground(alpha: Float) {
        topViewBinding.ivForeground.alpha = alpha
    }


}