package com.yzx.module_login

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.viewModelScope
import com.yzx.lib_base.base.BaseViewModel
import com.yzx.lib_base.http.BaseResult
import com.yzx.lib_base.mvvm.MvvmModel
import com.yzx.module_login.api.LoginApi
import com.yzx.module_login.model.LoginResponse
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author yzx
 * @date 2020/5/12
 * Description 登录ViewModel
 */
class LoginViewModel : BaseViewModel() {

    companion object {
        const val TAG = "LoginViewModel"
    }

    var loginResponseLiveData: MutableLiveData<LoginResponse> = MutableLiveData()
    private val loginApi: LoginApi by lazy {
        retrofit.create(LoginApi::class.java)
    }

    fun login(phoneNum: String, password: String) {
        showLoading()
        viewModelScope.launch(coroutineExceptionHandler) {
            dealResponse(loginApi.login(phoneNum, password))
        }
    }

    override fun onSuccess(response: BaseResult) {
        super.onSuccess(response)
        loginResponseLiveData.value = response as LoginResponse
    }

}