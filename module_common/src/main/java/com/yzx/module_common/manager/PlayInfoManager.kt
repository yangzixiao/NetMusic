package com.yzx.module_common.manager

import com.yzx.module_common.model.Track

object PlayInfoManager {

    private var playList: List<Track>? = null
    private var position = 0

    fun setPlayList(newPlaylist: List<Track>) {
        if (playList == newPlaylist) {
            return
        }
        playList = newPlaylist
    }

    fun getPlayList(): List<Track>? {
        return playList
    }

    fun setPosition(newPosition: Int) {
        this.position = newPosition
    }

    fun getPosition(): Int {
        return position
    }

    /**
     * 获取歌曲信息
     */
    fun getTrack(): Track? {
        return playList?.get(position)
    }
}