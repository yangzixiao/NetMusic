package com.yzx.module_common.play

import android.animation.ObjectAnimator
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.yzx.lib_base.arouter.ARouterPath
import com.yzx.lib_base.base.BaseActivity
import com.yzx.lib_base.ext.dp
import com.yzx.lib_base.ext.getScreenWidth
import com.yzx.lib_base.ext.toast
import com.yzx.lib_base.utils.ColorUtils
import com.yzx.lib_base.utils.glide.ColorCallBack
import com.yzx.lib_base.utils.glide.DrawableCallBack
import com.yzx.lib_base.utils.glide.GlideUtils
import com.yzx.module_common.R
import com.yzx.module_common.databinding.ActivityPlayBinding
import com.yzx.module_common.manager.PlayInfoManager
import org.koin.androidx.viewmodel.ext.android.viewModel

@Route(path = ARouterPath.COMMON_PLAY)
class PlayActivity : BaseActivity(), View.OnClickListener {

    override fun getActivityEnterAnim(): Int {
        return R.anim.slide_in_bottom
    }

    override fun getActivityExitAnim(): Int {
        return R.anim.slide_out_bottom
    }

    private lateinit var binding: ActivityPlayBinding
    private var oldPoster: Any = R.drawable.cd1
    private var posterAnimator: ObjectAnimator? = null
    private val viewModel: PlayViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTransparentStatus()
        setStatusWhiteFont()
        binding = ActivityPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel(viewModel)
        viewModel.musicUrlLiveData.observe(this, Observer {
            toast(it.url)
        })
        setupPoster()
        initView()
    }

    private fun initView() {
        binding.apply {
            initStatus(ivStatus)
            initToolbar(toolbar, R.menu.menu_play)

            layoutPlayIcon.apply {
                ivPlayModel.setOnClickListener(this@PlayActivity)
                ivPlayPrevious.setOnClickListener(this@PlayActivity)
                ivPlayPause.setOnClickListener(this@PlayActivity)
                ivPlayNext.setOnClickListener(this@PlayActivity)
                ivPlayMenu.setOnClickListener(this@PlayActivity)
            }

            layoutPlayAlbum.apply {
                playSpecialEffect.setStartSize(getScreenWidth() / 2 - 50f.dp)
                ivLike.setOnClickListener(this@PlayActivity)
                ivDownload.setOnClickListener(this@PlayActivity)
                ivComment.setOnClickListener(this@PlayActivity)
                ivPlayAlbumMore.setOnClickListener(this@PlayActivity)
            }
        }
    }

    private fun initPosterAnimator() {
        posterAnimator =
            ObjectAnimator.ofFloat(binding.layoutPlayAlbum.ivPoster, "rotation", 0f, 360f)
        posterAnimator!!.apply {
            repeatCount = -1
            interpolator = LinearInterpolator()
            duration = 20000
        }
    }

    private fun changeMusicLogic() {
        setupPoster()
        if (!PlayInfoManager.getPlayState()) {
            onClickPlayPauseIcon()
        }
    }

    override fun onResume() {
        super.onResume()
        setupPlayPauseIconAndPosterAnimatorByPlayState()
    }

    override fun onPause() {
        super.onPause()
        binding.layoutPlayAlbum.playSpecialEffect.release()
    }

    private fun onClickPlayPauseIcon() {
        PlayInfoManager.changePlayState()
        setupPlayPauseIconAndPosterAnimatorByPlayState()
    }

    /**
     * 根据播放状态设置播放按钮和海报动画
     */
    private fun setupPlayPauseIconAndPosterAnimatorByPlayState() {
        binding.layoutPlayIcon.ivPlayPause.setImageResource(
            if (PlayInfoManager.getPlayState()) R.drawable.c_p else R.drawable.c_r
        )
        if (posterAnimator == null) {
            initPosterAnimator()
        }
        val playSpecialEffect = binding.layoutPlayAlbum.playSpecialEffect
        if (PlayInfoManager.getPlayState()) {
            playSpecialEffect.start()
            if (posterAnimator!!.isStarted) {
                posterAnimator?.resume()
            } else {
                posterAnimator?.start()
            }
        } else {
            playSpecialEffect.stop()
            posterAnimator?.pause()
        }
    }

    /**
     * 设置海报
     */
    private fun setupPoster() {
        val track = PlayInfoManager.getTrack()
//        viewModel.getMusicUrl(track!!.id)
        val posterUrl = track?.al?.picUrl
        binding.apply {
            tvTitle.text = track?.name
            tvSubTitle.text = track!!.ar[0].name
            GlideUtils.getBitmapColor(posterUrl, ivBackground1, object : ColorCallBack {
                override fun onCallBack(color: Int) {
                    binding.layoutPlayAlbum.playSpecialEffect.setWaveColor(
                        ColorUtils.getColorByAlpha(color, 1f)
                    )
                }
            })
            GlideUtils.simpleLoadImg(oldPoster, ivBackground1)
            GlideUtils.loadBlurImage(posterUrl, ivBackground1, object : DrawableCallBack {
                override fun onGetDrawable(drawable: Drawable?) {
                    if (drawable != null) {
                        oldPoster = drawable
                        GlideUtils.loadImgWithAnim(drawable, ivBackground2)
                    }
                }
            })
            GlideUtils.loadImg(
                posterUrl, GlideUtils.TYPE_PLAY_ALBUM, layoutPlayAlbum.ivPoster,
            )
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            //播放控制点击事件
            R.id.ivPlayModel -> {

            }
            R.id.ivPlayPrevious -> {
                PlayInfoManager.playPrevious()
                changeMusicLogic()
            }
            R.id.ivPlayPause -> {
                onClickPlayPauseIcon()
            }
            R.id.ivPlayNext -> {
                PlayInfoManager.playNext()
                changeMusicLogic()
            }
            R.id.ivPlayMenu -> {

            }
            //播放封面menu
            R.id.ivLike -> {

            }
            R.id.ivDownload -> {

            }
            R.id.ivComment -> {

            }
            R.id.ivPlayAlbumMore -> {

            }
        }
    }
}