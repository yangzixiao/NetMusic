package com.yzx.lib_base.base

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope

import com.google.gson.JsonParseException
import com.yzx.lib_base.R

import com.yzx.lib_base.http.BaseResult
import com.yzx.lib_base.mvvm.MvvmModel
import com.yzx.lib_base.utils.LogUtils
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.cancel
import org.json.JSONException
import retrofit2.HttpException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.UnknownHostException
import java.text.ParseException

/**
 * @author yzx
 * @date 2019/12/23
 * Description
 */
open class BaseViewModel : MvvmModel() {
    val coroutineExceptionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        dealException(TAG, throwable as Exception)
    }
    var toastStringMsg: MutableLiveData<String> = MutableLiveData()
    var toastStringResourceMsg: MutableLiveData<Int> = MutableLiveData()
    var loadingState: MutableLiveData<Boolean> = MutableLiveData()

    fun showLoading() {
        loadingState.postValue(true)
    }

    fun hideLoading() {
        loadingState.postValue(false)
    }

    fun showToast(msg: String?) {
        toastStringMsg.postValue(msg)
    }

    fun showToast(@StringRes msg: Int) {
        toastStringResourceMsg.postValue(msg)
    }


    private fun  isAllRequestSuccess(responses: List<BaseResult>): Boolean {

        responses.forEach {
            if ((it.code != 200) && (it.code != 1)) {
                return false
            }
        }
        return true
    }

    private fun  getErrorMsg(responses: List<BaseResult>): String {
        responses.forEach {
            if ((it.code != 200) && (it.code != 1)) {
                return it.message
            }
        }
        return ""
    }

    fun dealResponse(response: BaseResult) {
        if (response.code == 200) {
            onSuccess(response)
        } else {
            onFail(response.message)
        }
    }

    fun dealResponse(responses: List<BaseResult>) {
        if (isAllRequestSuccess(responses)) {
            onSuccess(responses)
        } else {
            onFail(getErrorMsg(responses))
        }

    }

    open fun  onSuccess(responses: List<BaseResult>) {
        hideLoading()
    }


    open fun  onSuccess(response: BaseResult) {
        hideLoading()
    }

    open fun onFail(msg: String) {
        showToast(msg)
        hideLoading()
    }


    private fun dealException(tag: String, e: Exception) {
        hideLoading()
        LogUtils.e(tag, "dealException$e")
        //协程取消报错
        if (e is CancellationException) {
            return
        }

        showToast("${getErrorMsgByExceptionType(e)}$e")
    }

    private fun getErrorMsgByExceptionType(e: Exception): Int {

        return when (e) {
            is HttpException -> R.string.wlyc
            is ConnectException, is UnknownHostException -> R.string.ljcw
            is InterruptedIOException -> R.string.ljcs
            is JsonParseException, is JSONException, is ParseException -> R.string.jscw
            else -> R.string.wzcw
        }
    }

    fun cancelRequest() {
        viewModelScope.cancel(null)
    }
}