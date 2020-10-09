package com.yzx.lib_base

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.lifecycle.Observer
import com.yzx.lib_base.arouter.ARouterNavUtils
import com.yzx.lib_base.base.BaseActivity
import com.yzx.lib_base.databinding.ActivityBottomSongInfoBinding
import com.yzx.lib_base.ext.e
import com.yzx.lib_base.utils.glide.GlideUtils
import com.yzx.lib_play_client.PlayerManager
import com.yzx.lib_play_client.client.bean.base.BaseAlbumItem
import com.yzx.lib_play_client.client.bean.base.BaseArtistItem
import com.yzx.lib_play_client.client.bean.base.BaseMusicItem
import com.yzx.lib_play_client.client.bean.dto.ChangeMusic
import com.yzx.lib_play_client.client.bean.dto.PlayingMusic


/**
 * @author yzx
 * @date 2020/4/22
 * Description 底部有
 */
open class BaseBottomSongInfoActivity : BaseMusicInfoChangedActivity() {

    private lateinit var bottomSongInfoBinding: ActivityBottomSongInfoBinding

    /**
     * 由于使用lambda表达式实例化的对象，竟然是同一个实例导致的LiveData错误
     * https://blog.csdn.net/weixin_36762615/article/details/106719262
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bottomSongInfoBinding = ActivityBottomSongInfoBinding.inflate(layoutInflater)
        bottomSongInfoBinding.layoutBottomMusicInfo.root.visibility =
            if (PlayerManager.getInstance().changeMusicLiveData.value == null) View.GONE else View.VISIBLE

    }

    fun setBottomActivityContentView(contentView: View?) {
        if (contentView != null) {
            bottomSongInfoBinding.contentView.removeAllViews()
            bottomSongInfoBinding.contentView.addView(contentView,
                FrameLayout.LayoutParams(-1, -1))
        }
        setContentView(bottomSongInfoBinding.root)

        bottomSongInfoBinding.layoutBottomMusicInfo.apply {
            playPauseView.setOnClickListener {
                PlayerManager.getInstance().togglePlay()
            }
        }
    }

    override fun onMusicProgressChanged(playingMusic: PlayingMusic<BaseArtistItem, BaseAlbumItem<*, *>>?) {
        super.onMusicProgressChanged(playingMusic)
        if (playingMusic != null) {
            bottomSongInfoBinding.layoutBottomMusicInfo.playPauseView.setMaxAndProgress(playingMusic.duration.toFloat(),
                playingMusic.playerPosition.toFloat())
        }
    }

    override fun onMusicChanged(changeMusic: ChangeMusic<BaseAlbumItem<*, *>, BaseMusicItem<*>, BaseArtistItem>?) {
        super.onMusicChanged(changeMusic)
        bottomSongInfoBinding.layoutBottomMusicInfo.root.visibility = if (changeMusic == null) View.GONE else View.VISIBLE
        if (changeMusic != null) {
            bottomSongInfoBinding.layoutBottomMusicInfo.apply {
                tvSingerName.text = changeMusic.artist.name
                tvSongName.text = changeMusic.title
                GlideUtils.loadImg(changeMusic.img, ivSongPost)
                root.setOnClickListener {
                    ARouterNavUtils.navToPlay(changeMusic.musicId.toLong(), changeMusic.img)
                }
            }
        }
    }


    override fun onPlayPauseStateChanged(isPlaying: Boolean) {
        super.onPlayPauseStateChanged(isPlaying)
        bottomSongInfoBinding.layoutBottomMusicInfo.playPauseView.setPlayingState(isPlaying)
    }
}

