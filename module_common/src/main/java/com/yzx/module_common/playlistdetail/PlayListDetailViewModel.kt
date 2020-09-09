package com.yzx.module_common.playlistdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yzx.lib_base.base.BaseViewModel
import com.yzx.module_common.model.PlayListDetailResponse
import kotlinx.coroutines.launch

class PlayListDetailViewModel(private val playListDetailRepository: PlayListDetailRepository) :
    BaseViewModel() {

    val playListDetailLiveData = MutableLiveData<PlayListDetailResponse>()

    fun getPlayListDetail(id: Long) {
        viewModelScope.launch(coroutineExceptionHandler) {
            playListDetailLiveData.value = playListDetailRepository.getPlayListDetail(id)
        }
    }

}