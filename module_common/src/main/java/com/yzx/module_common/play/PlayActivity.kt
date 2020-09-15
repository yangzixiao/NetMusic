package com.yzx.module_common.play

import android.graphics.drawable.Drawable
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.yzx.lib_base.arouter.ARouterPath
import com.yzx.lib_base.base.BaseActivity
import com.yzx.lib_base.utils.glide.DrawableCallBack
import com.yzx.lib_base.utils.glide.GlideUtils
import com.yzx.module_common.R
import com.yzx.module_common.adpter.PlayAlbumMenuAdapter
import com.yzx.module_common.databinding.ActivityPlayBinding
import com.yzx.module_common.manager.PlayInfoManager

@Route(path = ARouterPath.COMMON_PLAY)
class PlayActivity : BaseActivity() {

    private lateinit var binding: ActivityPlayBinding

    private var oldPoster: Any = R.drawable.cd1

    private var position = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTransparentStatus()
        setStatusWhiteFont()
        binding = ActivityPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        PlayInfoManager.getPlayList()
        changePoster()
        binding.floating.setOnClickListener {
            position += 1
            PlayInfoManager.setPosition(position)

            changePoster()
        }
    }

    private fun initView() {
        binding.apply {
            initStatus(ivStatus)
            initToolbar(toolbar, R.menu.menu_play)

            layoutPlayAlbum.apply {
                recyclerView.adapter=PlayAlbumMenuAdapter(mutableListOf(R.drawable.cac,R.drawable.cap,R.drawable.ca7,R.drawable.cb9))
            }


        }
    }


    private fun changePoster() {

        val track = PlayInfoManager.getTrack()
        val posterUrl = track?.al?.picUrl
        binding.apply {

            tvTitle.text = track?.name
            tvSubTitle.text = track!!.ar[0].name
            GlideUtils.loadImg(
                posterUrl, GlideUtils.TYPE_PLAY_ALBUM, layoutPlayAlbum.ivPoster,
            )
            GlideUtils.simpleLoadImg(oldPoster, ivBackground1)
            GlideUtils.loadBlurImage(posterUrl, ivBackground1, object : DrawableCallBack {
                override fun onGetDrawable(drawable: Drawable?) {
                    if (drawable != null) {
                        oldPoster = drawable
                        GlideUtils.loadImgWithAnim(drawable, ivBackground2)
                    }
                }
            })
        }
    }
}