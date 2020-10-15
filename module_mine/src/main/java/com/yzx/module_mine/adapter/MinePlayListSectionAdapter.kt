package com.yzx.module_mine.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.noober.background.drawable.DrawableCreator
import com.yzx.lib_base.arouter.ARouterNavUtils
import com.yzx.lib_base.ext.dp
import com.yzx.lib_base.manager.UserInfoManager
import com.yzx.lib_base.utils.glide.GlideUtils
import com.yzx.module_mine.R
import com.yzx.module_mine.model.net.PlaylistBean
import com.yzx.module_mine.model.net.Result
import com.yzx.module_mine.model.section.MinePlayListHeadBean
import com.yzx.module_mine.model.section.MinePlayListSection

class MinePlayListSectionAdapter(headLayout: Int, itemLayout: Int, playListSections: MutableList<MinePlayListSection>? = null) :
    BaseSectionQuickAdapter<MinePlayListSection, BaseViewHolder>(headLayout, playListSections) {

    private var lastItemDrawable: Drawable = DrawableCreator.Builder().setSolidColor(Color.WHITE).setCornersRadius(6f.dp, 6f.dp, 0f, 0f)
        .build()
    private var normalItemDrawable: Drawable = ColorDrawable(Color.WHITE)

    init {
        setNormalLayout(itemLayout)
        addChildClickViewIds(R.id.ivAdd,R.id.ivMore)
    }

    override fun convert(holder: BaseViewHolder, item: MinePlayListSection) {
        var playListId = 0L
        var coverUrl = ""
        var title = ""
        var subTitle = ""
        //我的歌单数据
        val playlistBean = item.data
        if (playlistBean is PlaylistBean) {
            playlistBean.apply {
                title = name
                subTitle =
                    if (creator.userId == UserInfoManager.userInfoLiveData.value!!.uid) "${trackCount}首" else "${trackCount}首 by ${creator.nickname}"
                playListId = id
                coverUrl = coverImgUrl
            }
        } else if (playlistBean is Result) {//推荐歌单数据
            playlistBean.apply {
                title = name
                subTitle = "推荐歌单"
                playListId = id
                coverUrl = picUrl
            }
        }
        GlideUtils.loadImg(coverUrl, holder.getView(R.id.ivSongSheetPost))
        holder.setText(R.id.tvTitle, title)
        holder.setText(R.id.tvSubTitle, subTitle)
        holder.itemView.setOnClickListener {
            ARouterNavUtils.navToPlayListDetails(playListId, coverUrl)
        }
        holder.itemView.background = if (item.isLast) lastItemDrawable else normalItemDrawable

    }

    override fun convertHeader(helper: BaseViewHolder, item: MinePlayListSection) {
        val minePlayListHeadBean = item.data as MinePlayListHeadBean
        helper.setText(R.id.tvTitle, "${minePlayListHeadBean.title}(${minePlayListHeadBean.count}个)")
        helper.setVisible(R.id.ivAdd, item.type == MinePlayListSection.TYPE_CREATE)
    }
}