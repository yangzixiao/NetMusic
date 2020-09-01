package com.yzx.module_common

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.yzx.lib_base.arouter.ARouterPath
import com.yzx.lib_base.base.BaseActivity
import com.yzx.module_common.databinding.ActivityPlaylistDetailBinding

@Route(path = ARouterPath.COMMON_PLAYLIST_DETAIL)
class PlayListDetailActivity : BaseActivity() {

    private lateinit var binding: ActivityPlaylistDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTransparentStatus()
        setStatusDarkFont()
        binding = ActivityPlaylistDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}