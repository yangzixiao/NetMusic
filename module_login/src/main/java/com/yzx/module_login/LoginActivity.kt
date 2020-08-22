package com.yzx.module_login

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.yzx.lib_base.ARouter.ARouterNavUtils
import com.yzx.lib_base.ARouter.ARouterPath.LOGIN
import com.yzx.lib_base.ARouter.ARouterPath.MAIN
import com.yzx.lib_base.base.BaseActivity
import com.yzx.lib_base.mmkv.MmkvUtils
import com.yzx.lib_base.model.UserDataBean
import com.yzx.lib_base.utils.FormatUtil
import com.yzx.lib_base.utils.JsonUtils
import com.yzx.module_login.Constants.KEY_IS_TOURIST
import com.yzx.module_login.Constants.KEY_LOGIN_RESPONSE
import com.yzx.module_login.databinding.ActivityLoginBinding


/**
 * @author yzx
 * @date 2020/5/12
 * Description 登录页面
 */
@Route(path = LOGIN)
class LoginActivity : BaseActivity<LoginViewModel>() {
    private lateinit var loginBinding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusDarkFont()
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        viewModel.loginResponseLiveData.observe(this, Observer {

            if (it.code==200) {
                UserDataUtils.updateTouristState(false)
                UserDataUtils.initUserInfo( it)
            }
            ARouterNavUtils.normalNav(MAIN)
            finish()
        })

        loginBinding.etPhoneNum.doAfterTextChanged {
            loginBinding.ivClearPhoneNum.visibility =
                if (TextUtils.isEmpty(it)) View.GONE else View.VISIBLE
        }
        loginBinding.ivClearPhoneNum.setOnClickListener {
            loginBinding.etPhoneNum.text = null
        }
        loginBinding.tvLogin.setOnClickListener {
            val phoneNum = getEditContent(loginBinding.etPhoneNum)
            if (phoneNum.isNullOrBlank()) {
                toast(getString(R.string.InputPhoneNum))
                return@setOnClickListener
            }

            if (!FormatUtil.isMobileNO(phoneNum)) {
                toast(getString(R.string.InputRightPhoneNum))
                return@setOnClickListener
            }

            val password = getEditContent(loginBinding.etPassword)
            if (password.isNullOrBlank()) {
                toast(getString(R.string.InputPassword))
                return@setOnClickListener
            }

            viewModel.login(phoneNum, password)
        }


    }
}