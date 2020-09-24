package com.yzx.module_common.play

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yzx.lib_base.base.BaseViewModel
import com.yzx.lib_base.http.BaseResult
import com.yzx.module_common.model.MusicUrlInfo
import com.yzx.module_common.model.MusicUrlResponse
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.handleCoroutineException
import kotlinx.coroutines.launch

class PlayViewModel(private val playRepository: PlayRepository) : BaseViewModel() {
    val musicUrlLiveData = MutableLiveData<MusicUrlInfo>()
    private var musicUrlJob: Job? = null
    fun getMusicUrl(id: Long?) {
        if (id==null){
            return
        }
        if (musicUrlJob != null && !musicUrlJob!!.isActive) {
            musicUrlJob?.cancel()
        }
        musicUrlJob = viewModelScope.launch(coroutineExceptionHandler) {
            dealResponse(playRepository.getMusicUrl(id))
        }
    }

    override fun onSuccess(response: BaseResult) {
        super.onSuccess(response)
        if (response is MusicUrlResponse) {
            musicUrlLiveData.value = response.data[0]
        }
    }

}