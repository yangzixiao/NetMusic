package com.yzx.lib_base

import android.os.Bundle
import androidx.lifecycle.Observer
import com.yzx.lib_base.base.BaseActivity
import com.yzx.lib_play_client.PlayerManager
import com.yzx.lib_play_client.client.bean.base.BaseAlbumItem
import com.yzx.lib_play_client.client.bean.base.BaseArtistItem
import com.yzx.lib_play_client.client.bean.base.BaseMusicItem
import com.yzx.lib_play_client.client.bean.dto.ChangeMusic
import com.yzx.lib_play_client.client.bean.dto.PlayingMusic

/**
 * 音乐信息改变Activity
 */
open class BaseMusicInfoChangedActivity : BaseActivity() {

    private val playingMusicObserver: Observer<PlayingMusic<BaseArtistItem, BaseAlbumItem<*, *>>> = Observer { playingMusic ->
//        e("${this::class.java.simpleName}playingMusicObserver")
        onMusicProgressChanged(playingMusic)
    }

    private val playPauseStateObserver: Observer<Boolean> = Observer { isPause ->
        onPlayPauseStateChanged(!isPause)
    }

    private val loadingStateObserver: Observer<Boolean> = Observer { isLoading ->
        onLoadingStateChanged(isLoading)
    }

    private val changeMusicObserver: Observer<ChangeMusic<BaseAlbumItem<*, *>, BaseMusicItem<*>, BaseArtistItem>> = Observer { changeMusic ->
        onMusicChanged(changeMusic)
    }

    /**
     * 由于使用lambda表达式实例化的对象，竟然是同一个实例导致的LiveData错误
     * https://blog.csdn.net/weixin_36762615/article/details/106719262
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PlayerManager.getInstance().changeMusicLiveData.observe(this, changeMusicObserver)
        PlayerManager.getInstance().pauseLiveData.observe(this, playPauseStateObserver)
        PlayerManager.getInstance().loadingLiveData.observe(this, loadingStateObserver)
    }

    override fun onResume() {
        super.onResume()
        PlayerManager.getInstance().playingMusicLiveData.observe(this, playingMusicObserver)
    }

    override fun onPause() {
        super.onPause()
        PlayerManager.getInstance().playingMusicLiveData.removeObserver(playingMusicObserver)
    }

    /**
     * 音乐进度改变
     */
    open fun onMusicProgressChanged(playingMusic: PlayingMusic<BaseArtistItem, BaseAlbumItem<*, *>>?) {

    }

    /**
     * 音乐信息改变
     */
    open fun onMusicChanged(changeMusic: ChangeMusic<BaseAlbumItem<*, *>, BaseMusicItem<*>, BaseArtistItem>?) {

    }

    open fun onLoadingStateChanged(isLoading: Boolean) {

    }

    /**
     * 音乐播放暂停状态改变
     */
    open fun onPlayPauseStateChanged(isPlaying: Boolean) {

    }
}