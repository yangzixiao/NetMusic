package com.yzx.lib_base.model

import androidx.appcompat.widget.DialogTitle

class CommonPlayListBean(id: String, title: String, subTitle: String, posterUrl: String) {
    companion object {
        //歌单类型
        /**
         * 我的页面，未登录状态下推荐歌单
         */
        const val TYPE_MINE_PAGER_RECOMMEND = 1

        /**
         * 我的页面，我创建的歌单
         */
        const val TYPE_MINE_PAGER_CREATE = 2

        /**
         * 我的页面，我收藏的歌单
         */
        const val TYPE_MINE_PAGER_COLLECT = 3
    }
}