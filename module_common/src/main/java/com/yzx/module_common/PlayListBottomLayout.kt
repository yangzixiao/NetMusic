package com.yzx.module_common

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.qmuiteam.qmui.nestedScroll.QMUIContinuousNestedBottomDelegateLayout
import com.qmuiteam.qmui.nestedScroll.QMUIContinuousNestedBottomRecyclerView
import com.yzx.lib_base.ext.dp
import com.yzx.lib_base.ext.getDefaultStatusAndToolbarHeight
import com.yzx.lib_base.utils.glide.GlideUtils
import com.yzx.module_common.adpter.SongAdapter
import com.yzx.module_common.databinding.LayoutPlayListHeadBinding
import com.yzx.module_common.model.PlayListDetailResponse
import com.yzx.module_common.model.Track


class PlayListBottomLayout(
    context: Context,
    attrs: AttributeSet? = null,
    defaultAttr: Int = 0
) :
    QMUIContinuousNestedBottomDelegateLayout(
        context,
        attrs,
        defaultAttr
    ) {

    companion object {

        private val menuIcons = mutableListOf(
            R.drawable.ic_comment,
            R.drawable.ic_share,
            R.drawable.ic_download,
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
        recyclerView.adapter = SongAdapter()
        recyclerView.layoutManager = LinearLayoutManager(context)
        return recyclerView
    }

    fun addData(tracks: List<Track>) {
        val songAdapter = recyclerView.adapter as SongAdapter
        songAdapter.addData(tracks)
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
                text =
                    if (description.isEmpty()) context.getString(R.string.EditInfo) else description
                setOnClickListener {

                }
            }
            tvCount.text = "(${playlist.trackCount})首"
            playListTab.getTabAt(0)!!.text = playlist.commentCount.toString()
            playListTab.getTabAt(1)!!.text = playlist.shareCount.toString()
            tvCollect.text = "${context.getText(R.string.Collect)}(${playlist.subscribedCount}首)"
        }
    }

    fun updateCover(coverImgUrl: String) {
        topViewBinding.apply {
            GlideUtils.loadImg(coverImgUrl, ivPoster)
            GlideUtils.loadDrawable(coverImgUrl, ivBackground, 120, 15)
            GlideUtils.loadDrawable(coverImgUrl, ivForeground, 120, 15)

        }
    }

    fun updateForeground(alpha: Float) {
        topViewBinding.ivForeground.alpha = alpha
    }


}