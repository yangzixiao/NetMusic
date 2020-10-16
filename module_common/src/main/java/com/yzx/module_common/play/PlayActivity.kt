package com.yzx.module_common.play

import android.animation.ObjectAnimator
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import com.alibaba.android.arouter.facade.annotation.Route
import com.yzx.lib_base.BaseMusicInfoChangedActivity
import com.yzx.lib_base.arouter.ARouterPath
import com.yzx.lib_core.ext.dp
import com.yzx.lib_core.ext.getScreenWidth
import com.yzx.lib_core.utils.ColorUtils
import com.yzx.lib_base.utils.glide.ColorCallBack
import com.yzx.lib_base.utils.glide.DrawableCallBack
import com.yzx.lib_base.utils.glide.GlideUtils
import com.yzx.lib_base.widget.musicwidget.slider.MusicSlider
import com.yzx.lib_play_client.PlayerManager
import com.yzx.lib_play_client.client.PlayingInfoManager.RepeatMode
import com.yzx.lib_play_client.client.bean.base.BaseAlbumItem
import com.yzx.lib_play_client.client.bean.base.BaseArtistItem
import com.yzx.lib_play_client.client.bean.base.BaseMusicItem
import com.yzx.lib_play_client.client.bean.dto.ChangeMusic
import com.yzx.lib_play_client.client.bean.dto.PlayingMusic
import com.yzx.module_common.R
import com.yzx.module_common.databinding.ActivityPlayBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

@Route(path = ARouterPath.COMMON_PLAY)
class PlayActivity : BaseMusicInfoChangedActivity(), View.OnClickListener {

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

        playManager.playModeLiveData.observe(this, {
            binding.layoutPlayIcon.ivPlayModel.setImageResource(
                when (it) {
                    RepeatMode.ONE_LOOP ->
                        R.drawable.cbc
                    RepeatMode.RANDOM ->
                        R.drawable.cbv
                    else ->
                        R.drawable.cb3
                }
            )
        })
        initView()
    }


    override fun onPlayPauseStateChanged(isPlaying: Boolean) {
        super.onPlayPauseStateChanged(isPlaying)
        setupPlayPauseIconByPlayPauseState(isPlaying)
        setupPosterAnimatorAndEffectByPlayPauseAndLoadingState(isPlaying, PlayerManager.getInstance().loadingLiveData.value ?: false)
    }


    override fun onLoadingStateChanged(isLoading: Boolean) {
        super.onLoadingStateChanged(isLoading)
        setupPosterAnimatorAndEffectByPlayPauseAndLoadingState(PlayerManager.getInstance().isPlaying, isLoading)
        binding.sliderSongDuration.setState(
            if (isLoading && !PlayerManager.getInstance().isPaused) MusicSlider.MUSIC_STATE.LOADING else MusicSlider.MUSIC_STATE.SUCCESS)
    }

    /**
     * 音乐改变---设置海报，重置音乐进度条，停止音乐动效
     */
    override fun onMusicChanged(changeMusic: ChangeMusic<BaseAlbumItem<*, *>, BaseMusicItem<*>, BaseArtistItem>?) {
        super.onMusicChanged(changeMusic)
        if (changeMusic != null) {
            setupPoster(changeMusic)
            binding.sliderSongDuration.value = 0f
            binding.sliderSongDuration.valueTo = changeMusic.duration.toFloat()
            binding.sliderSongDuration.valueFrom = 0f
            binding.sliderSongDuration.cache = 0f
            binding.sliderSongDuration.setState(MusicSlider.MUSIC_STATE.LOADING)
            setupPosterAnimatorAndEffectByPlayPauseAndLoadingState(false, false)
        }
    }

    override fun onMusicProgressChanged(playingMusic: PlayingMusic<BaseArtistItem, BaseAlbumItem<*, *>>?) {
        super.onMusicProgressChanged(playingMusic)
        if (playingMusic != null) {
            val duration = playingMusic.duration.toFloat()
            val current = playingMusic.playerPosition.toFloat()
            binding.sliderSongDuration.valueTo = duration
            if (current <= duration) {
                binding.sliderSongDuration.value = current
            }
            binding.tvPlayedTime.text = playingMusic.nowTime
            binding.tvLeftTime.text = playingMusic.allTime
            binding.sliderSongDuration.cache =
                playingMusic.duration * (playingMusic.buffer.toFloat() / 100)
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


            sliderSongDuration.addOnChangeListener { _, value, fromUser ->
                if (fromUser) {
                    PlayerManager.getInstance().setSeek(value.toInt())
                    setupPosterAnimatorAndEffectByPlayPauseAndLoadingState(PlayerManager.getInstance().isPlaying,
                        PlayerManager.getInstance().loadingLiveData.value ?: true)
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
        setupPlayPauseIconByPlayPauseState(!(PlayerManager.getInstance().pauseLiveData.value ?: true))
    }

    override fun onPause() {
        super.onPause()
        binding.layoutPlayAlbum.playSpecialEffect.release()
    }

    /**
     * 根据播放状态设置icon
     */
    private fun setupPlayPauseIconByPlayPauseState(isPlaying: Boolean) {
        binding.layoutPlayIcon.ivPlayPause.setImageResource(
            if (isPlaying) R.drawable.c_p else R.drawable.c_r
        )
    }

    /**
     * 只有在加载完成并且播放状态才播放动画和特效
     */
    private fun setupPosterAnimatorAndEffectByPlayPauseAndLoadingState(isPlaying: Boolean, isLoading: Boolean) {
        if (posterAnimator == null) {
            initPosterAnimator()
        }
        val playSpecialEffect = binding.layoutPlayAlbum.playSpecialEffect
        if (isPlaying && !isLoading) {
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
    private fun setupPoster(
        changeMusic: ChangeMusic<BaseAlbumItem<*, *>, BaseMusicItem<*>, BaseArtistItem>) {
        val posterUrl = changeMusic.img
        binding.apply {
            tvTitle.text = changeMusic.title
            tvSubTitle.text = changeMusic.artist.name
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
            GlideUtils.getBitmapColor(posterUrl, ivBackground1, object : ColorCallBack {
                override fun onCallBack(color: Int) {
                    binding.layoutPlayAlbum.playSpecialEffect.setWaveColor(
                        ColorUtils.getColorByAlpha(color, 1f)
                    )
                }
            })
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            //播放控制点击事件
            R.id.ivPlayModel -> {
                PlayerManager.getInstance().changeMode()
            }
            R.id.ivPlayPrevious -> {
                PlayerManager.getInstance().playPrevious()
            }
            R.id.ivPlayPause -> {
                PlayerManager.getInstance().togglePlay()
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