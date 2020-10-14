package com.yzx.module_mine.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yzx.lib_base.base.BaseViewModel
import com.yzx.lib_base.http.BaseResult
import com.yzx.module_mine.model.MinePagerData
import com.yzx.module_mine.model.net.MinePagerRecommendPlayListResponse
import com.yzx.module_mine.model.net.PersonalFMResponse
import com.yzx.module_mine.model.net.PlayListResponse
import com.yzx.module_mine.repository.MineRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

/**
 * @author yzx
 * @date 2020/7/17
 * Description mineViewModel
 */
open class MineViewModel(private val mineRepository: MineRepository) : BaseViewModel() {

    companion object {
        const val TAG = "MineViewModel"
    }

    val minePagerLiveData: MutableLiveData<MinePagerData> = MutableLiveData()


    /**
     * 根据登录状态获取不同数据
     */
    fun getMinePagerData(uid: String? = null) {
        viewModelScope.launch(coroutineExceptionHandler) {

            val personalFMAsync = async {
                mineRepository.getPersonalFM()
            }
            val result = if (uid.isNullOrBlank()) {
                val recommendPlayListsAsync = async {
                    mineRepository.getRecommendPlayLists()
                }
                awaitAll(recommendPlayListsAsync, personalFMAsync)
            } else {
                val playListAsync = async {
                    mineRepository.getUserPlayLists(uid)
                }
                awaitAll(playListAsync, personalFMAsync)
            }
            dealResponse(result)
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