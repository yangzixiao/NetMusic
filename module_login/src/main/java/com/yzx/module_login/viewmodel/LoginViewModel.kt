package com.yzx.module_login.viewmodel

import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.viewModelScope
import com.yzx.lib_base.base.BaseViewModel
import com.yzx.lib_base.http.BaseResult

import com.yzx.module_login.model.LoginResponse
import com.yzx.module_login.repository.LoginRepository
import kotlinx.coroutines.launch

/**
 * @author yzx
 * @date 2020/5/12
 * Description 登录ViewModel
 */
class LoginViewModel(private val loginRepository: LoginRepository) : BaseViewModel() {

    companion object {
        const val TAG = "LoginViewModel"
    }

    var loginResponseLiveData: MutableLiveData<LoginResponse> = MutableLiveData()

    fun login(phoneNum: String, password: String) {
        showLoading()
        viewModelScope.launch(coroutineExceptionHandler) {
            dealResponse(loginRepository.login(phoneNum, password))
        }
    }

    override fun onSuccess(response: BaseResult) {
        super.onSuccess(response)
        loginResponseLiveData.value = response as LoginResponse
    }

}