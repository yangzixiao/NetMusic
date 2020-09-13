package com.yzx.module_common.adpter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yzx.lib_base.utils.glide.GlideUtils
import com.yzx.module_common.R
import com.yzx.module_common.model.Subscriber

class PlayListSubscriberAdapter(subscribers: MutableList<Subscriber>) :
    BaseQuickAdapter<Subscriber, BaseViewHolder>(R.layout.item_follower, data = subscribers) {
    override fun convert(holder: BaseViewHolder, item: Subscriber) {
        GlideUtils.loadImg(item.avatarUrl, holder.getView(R.id.ivHead))
    }

}