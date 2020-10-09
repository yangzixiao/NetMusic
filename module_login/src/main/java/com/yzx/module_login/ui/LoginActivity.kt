package com.yzx.module_login.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.core.widget.doAfterTextChanged
import com.alibaba.android.arouter.facade.annotation.Route
import com.yzx.lib_base.arouter.ARouterNavUtils
import com.yzx.lib_base.arouter.ARouterPath.LOGIN
import com.yzx.lib_base.arouter.ARouterPath.MAIN
import com.yzx.lib_base.arouter.ArouterNavKey.KEY_IS_FROM_Login_GUIDE
import com.yzx.lib_base.base.BaseActivity
import com.yzx.lib_base.ext.getContent
import com.yzx.lib_base.ext.toast
import com.yzx.lib_base.utils.FormatUtil
import com.yzx.module_login.R
import com.yzx.module_login.UserDataUtils
import com.yzx.module_login.databinding.ActivityLoginBinding
import com.yzx.module_login.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * @author yzx
 * @date 2020/5/12
 * Description 登录页面
 */
@Route(path = LOGIN)
class LoginActivity : BaseActivity() {

    private lateinit var loginBinding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModel()

    @SuppressLint("LogNotTimber")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusDarkFont()

        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)
        initViewModel(viewModel)
        viewModel.loginResponseLiveData.observe(this, {
            hideLoadingDialog()
            UserDataUtils.updateTouristState(false)
            UserDataUtils.initUserInfo(it)
            if (intent.getBooleanExtra(KEY_IS_FROM_Login_GUIDE,false)) {
                ARouterNavUtils.normalNav(MAIN)
            }
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
            val phoneNum = loginBinding.etPhoneNum.getContent()
            if (phoneNum.isBlank()) {
                toast(getString(R.string.InputPhoneNum))
                return@setOnClickListener
            }

            if (!FormatUtil.isMobileNO(phoneNum)) {
                toast(getString(R.string.InputRightPhoneNum))
                return@setOnClickListener
            }

            val password = loginBinding.etPassword.getContent()
            if (password.isBlank()) {
                toast(getString(R.string.InputPassword))
                return@setOnClickListener
            }
            showLoadingDialog()
            viewModel.login(phoneNum, password)
        }


    }
}