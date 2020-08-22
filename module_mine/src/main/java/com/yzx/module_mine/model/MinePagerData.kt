package com.yzx.module_mine.model

import com.yzx.lib_base.model.CommonPlayListBean
import com.yzx.module_mine.adapter.ItemPlayListBinder
import com.yzx.module_mine.model.net.PersonalFMBean
import com.yzx.module_mine.model.net.PlaylistBean
import com.yzx.module_mine.model.net.Result

data class MinePagerData(var personalFM: PersonalFMBean? = null,
    var playlist: List<PlaylistBean>? = null, var recommendPlaylist: List<Result>? = null)