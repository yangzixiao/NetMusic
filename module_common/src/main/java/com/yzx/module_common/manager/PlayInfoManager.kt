package com.yzx.module_common.manager

import com.yzx.module_common.model.Track

object PlayInfoManager {

//    private var playList: List<Track>? = null
//    private var position = 0
//    private var playState = false
//
//    fun setPlayList(newPlaylist: List<Track>) {
//        if (playList == newPlaylist) {
//            return
//        }
//        position = 0
//        playList = newPlaylist
//    }
//
//    fun getPlayList(): List<Track>? {
//        return playList
//    }
//
//    fun setPosition(newPosition: Int) {
//        if (playList == null) {
//            return
//        }
//        if (newPosition < 0) {
//            position = playList!!.size - 1
//            return
//        }
//
//        if (newPosition > playList!!.size - 1) {
//            position = 0
//            return
//        }
//        this.position = newPosition
//    }
//
//    fun getPosition(): Int {
//        return position
//    }
//
//    fun playNext() {
//        setPosition(position + 1)
//    }
//
//    fun playPrevious() {
//        setPosition(position - 1)
//    }
//
//    /**
//     * 获取歌曲信息
//     */
//    fun getTrack(): Track? {
//        return playList?.get(position)
//    }
//
//    fun getPlayState(): Boolean {
//        return playState
//    }
//
//    fun setPlayState(newPlayState: Boolean) {
//        playState = newPlayState
//    }
//
//    fun changePlayState() {
//        playState = !playState
//    }
}