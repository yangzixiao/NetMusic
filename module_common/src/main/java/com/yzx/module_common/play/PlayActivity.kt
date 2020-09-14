package com.yzx.module_common.play

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.yzx.lib_base.arouter.ARouterPath
import com.yzx.lib_base.arouter.ArouterNavKey.KEY_POSTER_URL
import com.yzx.lib_base.base.BaseActivity
import com.yzx.lib_base.utils.glide.GlideUtils
import com.yzx.module_common.databinding.ActivityPlayBinding

@Route(path = ARouterPath.COMMON_PLAY)
class PlayActivity : BaseActivity() {

    private lateinit var binding: ActivityPlayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTransparentStatus()
        setStatusWhiteFont()
        binding = ActivityPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val posterUrl = intent.getStringExtra(KEY_POSTER_URL)

        GlideUtils.loadDrawable(posterUrl,binding.ivBackground,100,6)

//        binding.toolbar.subtitle=SpannableStringBuilder().append().
    }
}