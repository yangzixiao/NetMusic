package com.yzx.module_common.playlistdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yzx.lib_base.base.BaseViewModel
import com.yzx.module_common.model.PlayListDetailResponse
import kotlinx.coroutines.launch

class PlayListDetailViewModel(private val playListDetailRepository: PlayListDetailRepository) :
    BaseViewModel() {

    private val ids = StringBuilder()
    val playListDetailLiveData = MutableLiveData<PlayListDetailResponse>()

    fun getPlayListDetail(id: Long) {
        showLoading()
        viewModelScope.launch(coroutineExceptionHandler) {
            val playListDetail = playListDetailRepository.getPlayListDetail(id)
            if (playListDetail.code == 200) {
                if (playListDetail.playlist.trackCount <= 10) {
                    getSongsUrl(ids, playListDetail)
                } else {
                    getAllSongsByIds(playListDetail)
                }
            } else {
                onFail(playListDetail.message)
            }
        }
    }

    /**
     * 获取所有歌曲url
     */
    private suspend fun getSongsUrl(ids: StringBuilder, playListDetail: PlayListDetailResponse) {
        if (ids.isEmpty()) {
            val trackIds = playListDetail.playlist.trackIds
            trackIds.forEachIndexed { index, trackId ->
                this.ids.append(trackId.id)
                if (trackIds.lastIndex != index) {
                    this.ids.append(",")
                }
            }
        }

        val playListUrls = playListDetailRepository.getPlayListUrls(this.ids.toString())

        if (playListUrls.code == 200) {
            playListDetail.playlist.tracks.forEach { track ->
                //歌单歌曲index和歌单url index并不是一一对应
                playListUrls.data.forEach { songUrlBean->
                    if (track.id == songUrlBean.id && songUrlBean.url != null) {
                        track.url = songUrlBean.url
                        return@forEach
                    }
                }
            }
            playListDetailLiveData.value = playListDetail
            hideLoading()
        } else {
            onFail(playListUrls.message)
        }
    }

    private suspend fun getAllSongsByIds(playListDetail: PlayListDetailResponse) {
        ids.clear()
        val trackIds = playListDetail.playlist.trackIds
        trackIds.forEachIndexed { index, trackId ->
            ids.append(trackId.id)
            if (trackIds.lastIndex != index) {
                ids.append(",")
            }
        }

        val playListSongsResponse = playListDetailRepository.getPlayListDetail(ids.toString())
        if (playListSongsResponse.code == 200) {
            playListDetail.playlist.tracks = playListSongsResponse.songs
            getSongsUrl(ids, playListDetail)
        } else {
            onFail(playListSongsResponse.message)
        }
    }

}