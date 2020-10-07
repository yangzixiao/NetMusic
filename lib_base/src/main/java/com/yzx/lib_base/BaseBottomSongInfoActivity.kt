package com.yzx.lib_base

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.yzx.lib_base.arouter.ARouterNavUtils
import com.yzx.lib_base.base.BaseActivity
import com.yzx.lib_base.databinding.ActivityBottomSongInfoBinding
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
open class BaseBottomSongInfoActivity : BaseActivity() {

    private lateinit var bottomSongInfoBinding: ActivityBottomSongInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomSongInfoBinding = ActivityBottomSongInfoBinding.inflate(layoutInflater)

        PlayerManager.getInstance().apply {
            playingMusicLiveData.observe(this@BaseBottomSongInfoActivity, object :Observer<PlayingMusic<BaseArtistItem,BaseAlbumItem<*, *>>>{
                override fun onChanged(playingMusic: PlayingMusic<BaseArtistItem, BaseAlbumItem<*, *>>?) {
                    onMusicProgressChanged(playingMusic)
                }
            })

            changeMusicLiveData.observe(this@BaseBottomSongInfoActivity,object :Observer<ChangeMusic<BaseAlbumItem<*, *>, BaseMusicItem<*>, BaseArtistItem>>{
                override fun onChanged(
                    changeMusic: ChangeMusic<BaseAlbumItem<*, *>, BaseMusicItem<*>, BaseArtistItem>?) {
                    onMusicChanged(changeMusic)
                }
            })
        }
    }

    fun setBottomActivityContentView(contentView: View?) {
        if (contentView != null) {
            bottomSongInfoBinding.contentView.removeAllViews()
            bottomSongInfoBinding.contentView.addView(contentView,
                FrameLayout.LayoutParams(-1, -1))
        }
        setContentView(bottomSongInfoBinding.root)
    }


    /**
     * 音乐进度改变
     */
    private fun onMusicProgressChanged(
        playingMusic: PlayingMusic<BaseArtistItem, BaseAlbumItem<*, *>>?) {

    }

    /**
     * 歌曲改变
     */
    private fun onMusicChanged(
        changeMusic: ChangeMusic<BaseAlbumItem<*, *>, BaseMusicItem<*>, BaseArtistItem>?) {
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

    override fun onDestroy() {
        super.onDestroy()
    }

}

