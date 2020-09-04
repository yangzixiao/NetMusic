package com.yzx.module_common

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.yzx.lib_base.arouter.ARouterPath
import com.yzx.lib_base.base.BaseActivity
import com.yzx.module_common.databinding.ActivityPlayListDetailBinding

@Route(path = ARouterPath.COMMON_PLAYLIST_DETAIL)
class PlayListDetailActivity : BaseActivity() {

    private lateinit var binding: ActivityPlayListDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayListDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTransparentStatus()
        setStatusDarkFont()
    }
}