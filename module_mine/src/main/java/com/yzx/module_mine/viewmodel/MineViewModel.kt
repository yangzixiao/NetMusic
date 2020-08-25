package com.yzx.module_mine.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yzx.lib_base.base.BaseViewModel
import com.yzx.lib_base.http.BaseResult
import com.yzx.module_mine.api.MineApi
import com.yzx.module_mine.model.MinePagerData
import com.yzx.module_mine.model.net.MinePagerRecommendPlayListResponse
import com.yzx.module_mine.model.net.PersonalFMResponse
import com.yzx.module_mine.model.net.PlayListResponse
import kotlinx.coroutines.*

/**
 * @author yzx
 * @date 2020/7/17
 * Description mineViewModel
 */
open class MineViewModel : BaseViewModel() {

    companion object {
        const val TAG = "MineViewModel"
    }

    private val mineApi by lazy {
        retrofit.create(MineApi::class.java)
    }
    val minePagerLiveData: MutableLiveData<MinePagerData> = MutableLiveData()
//    private val minePagerData = MinePagerData()

    /**
     *
     */
    fun getMinePagerData(uid: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            val playListAsync = async {
                mineApi.getUserPlayLists(uid, 1000)
            }
            val personalFMAsync = async {
                mineApi.getPersonalFM()
            }
            dealResponse(awaitAll(playListAsync, personalFMAsync))
        }
    }

    fun getMinePagerData() {
        viewModelScope.launch(coroutineExceptionHandler) {
            val personalFMAsync = async {
                mineApi.getPersonalFM()
            }
            val recommendPlayListsAsync = async {
                mineApi.getRecommendPlayLists(6)
            }
            dealResponse(awaitAll(recommendPlayListsAsync, personalFMAsync))
        }

    }

    override fun onSuccess(responses: List<BaseResult>) {
        super.onSuccess(responses)

        val minePagerData = MinePagerData()
        responses.forEach {

            when (it) {
                is PlayListResponse -> {
                    minePagerData.playlist = it.playlist
                }
                is PersonalFMResponse -> minePagerData.personalFM = it.data[0]
                is MinePagerRecommendPlayListResponse -> minePagerData.recommendPlaylist = it.result
            }
        }
        minePagerLiveData.value = minePagerData

    }

}