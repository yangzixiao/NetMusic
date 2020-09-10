package com.yzx.module_common

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

    private lateinit var topViewBinding: LayoutPlayListHeadBinding
    private lateinit var recyclerView: QMUIContinuousNestedBottomRecyclerView
    override fun onCreateHeaderView(): View {
        topViewBinding = LayoutPlayListHeadBinding.inflate(LayoutInflater.from(context))

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

    fun updateHeadView(playListDetailData: PlayListDetailResponse) {

    }

    fun updateCover(coverImgUrl: String) {
        topViewBinding.apply {
            GlideUtils.loadImg(coverImgUrl, ivPoster)
            GlideUtils.loadDrawable(coverImgUrl, ivBackground, 120, 15)
            GlideUtils.loadDrawable(coverImgUrl, ivForeground, 120, 15)

        }
    }

    fun updateForeground(alpha:Float){
        topViewBinding.ivForeground.alpha=alpha
    }


}