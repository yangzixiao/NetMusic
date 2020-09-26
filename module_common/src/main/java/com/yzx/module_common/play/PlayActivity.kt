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
import com.yzx.lib_base.ext.e
import com.yzx.lib_base.ext.getScreenWidth
import com.yzx.lib_base.ext.toast
import com.yzx.lib_base.utils.ColorUtils
import com.yzx.lib_base.utils.glide.ColorCallBack
import com.yzx.lib_base.utils.glide.DrawableCallBack
import com.yzx.lib_base.utils.glide.GlideUtils
import com.yzx.lib_base.widget.musicwidget.slider.MusicSlider
import com.yzx.lib_play_client.PlayerManager
import com.yzx.lib_play_client.client.bean.base.BaseAlbumItem
import com.yzx.lib_play_client.client.bean.base.BaseArtistItem
import com.yzx.lib_play_client.client.bean.base.BaseMusicItem
import com.yzx.lib_play_client.client.bean.dto.ChangeMusic
import com.yzx.lib_play_client.client.bean.dto.PlayingMusic
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
        val playManager = PlayerManager.getInstance()
        playManager.playingMusicLiveData.observe(this, {
            onProgressChanged(it)
        })

        playManager.changeMusicLiveData.observe(this, {
            setupPoster(it)
        })

        playManager.pauseLiveData.observe(this, {
            setupPlayPauseIconAndPosterAnimatorByPlayState(!it)
            e("$it")
        })

        initView()
    }

    private fun onProgressChanged(playingMusic: PlayingMusic<BaseArtistItem, BaseAlbumItem<*, *>>) {
        binding.sliderSongDuration.valueTo = playingMusic.duration.toFloat()
        binding.sliderSongDuration.value = playingMusic.playerPosition.toFloat()
        binding.tvPlayedTime.text = playingMusic.nowTime
        binding.tvLeftTime.text = playingMusic.allTime
        binding.sliderSongDuration.cache =
            playingMusic.duration * (playingMusic.buffer.toFloat() / 100)
        if (playingMusic.isLoading) {
            binding.layoutPlayAlbum.playSpecialEffect.stop()
            binding.sliderSongDuration.setState(MusicSlider.MUSIC_STATE.LOADING)
        } else {
            binding.sliderSongDuration.setState(MusicSlider.MUSIC_STATE.SUCCESS)
            binding.layoutPlayAlbum.playSpecialEffect.start()
        }
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


            sliderSongDuration.addOnChangeListener { slider, value, fromUser ->
                if (fromUser) {
                    PlayerManager.getInstance().setSeek(value.toInt())
                }
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

    override fun onResume() {
        super.onResume()
        setupPlayPauseIconAndPosterAnimatorByPlayState(PlayerManager.getInstance().isPlaying)
    }

    override fun onPause() {
        super.onPause()
        binding.layoutPlayAlbum.playSpecialEffect.release()
    }


    /**
     * 根据播放状态设置播放按钮和海报动画
     */
    private fun setupPlayPauseIconAndPosterAnimatorByPlayState(isPlaying: Boolean) {
        binding.layoutPlayIcon.ivPlayPause.setImageResource(
            if (isPlaying) R.drawable.c_p else R.drawable.c_r
        )
        if (posterAnimator == null) {
            initPosterAnimator()
        }
        val playSpecialEffect = binding.layoutPlayAlbum.playSpecialEffect
        if (isPlaying) {
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
    private fun setupPoster(changeMusic: ChangeMusic<BaseAlbumItem<*, *>, BaseMusicItem<*>, BaseArtistItem>) {

        val posterUrl = changeMusic.img
        binding.apply {
            tvTitle.text = changeMusic.title
            tvSubTitle.text = changeMusic.artist.name
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
                PlayerManager.getInstance().playPrevious()
            }
            R.id.ivPlayPause -> {
                if (PlayerManager.getInstance().isPlaying) {
                    PlayerManager.getInstance().pauseAudio()
                } else {
                    PlayerManager.getInstance().playAudio()
                }
            }
            R.id.ivPlayNext -> {
                PlayerManager.getInstance().playNext()
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

    override fun onDestroy() {
        super.onDestroy()
        binding.layoutPlayAlbum.playSpecialEffect.release()
        posterAnimator?.cancel()
    }
}