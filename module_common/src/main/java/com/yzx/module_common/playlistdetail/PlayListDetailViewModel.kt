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
                    playListDetailLiveData.value = playListDetail
                    hideLoading()
                } else {
                    getAllSongsByIds(playListDetail)
                }
            } else {
                onFail(playListDetail.message)
            }
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
            playListDetailLiveData.value = playListDetail
            hideLoading()
        } else {
            onFail(playListSongsResponse.message)
        }
    }

}