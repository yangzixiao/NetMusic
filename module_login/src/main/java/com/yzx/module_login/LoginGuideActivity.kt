package com.yzx.module_login

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.yzx.lib_base.ARouter.ARouterNavUtils
import com.yzx.lib_base.ARouter.ARouterPath
import com.yzx.lib_base.ARouter.ARouterPath.LOGIN_GUIDE
import com.yzx.lib_base.CommonVIewModel
import com.yzx.lib_base.base.BaseActivity
import com.yzx.lib_base.manager.UserInfoManager
import com.yzx.lib_base.mmkv.MmkvUtils
import com.yzx.module_login.databinding.ActivityLoginGuideBinding

/**
 * @author yzx
 * @date 2020/5/12
 * Description 登陆引导
 */
@Route(path = LOGIN_GUIDE)
class LoginGuideActivity : BaseActivity<CommonVIewModel>() {

    /**
     * 协议是否勾选
     */
    private var isAgreementChecked = false

    override fun getActivityEnterAnim(): Int {
        return R.anim.slide_in_bottom
    }

    override fun getActivityExitAnim(): Int {
        return R.anim.slide_out_bottom
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val loginGuideBinding = ActivityLoginGuideBinding.inflate(layoutInflater)
        setContentView(loginGuideBinding.root)
        setTransparentStatus()

        loginGuideBinding.tvPhoneNumLogin.setOnClickListener {

            if (isAgreementChecked) {
                ARouterNavUtils.normalNav(ARouterPath.LOGIN)
                finish()
            } else {
                shakeAgreementLayout(loginGuideBinding.layoutProtocol)
                Toast.makeText(
                    this, "请勾选${getString(R.string.Agree)}" +
                            "${getString(R.string.UserAgreement)}${getString(R.string.PrivacyAgreement)}${getString(
                                R.string.ChildPrivacyAgreement
                            )}", Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
        loginGuideBinding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            isAgreementChecked = isChecked
        }

        loginGuideBinding.tvTry.setOnClickListener {
            ARouterNavUtils.normalNav(ARouterPath.MAIN)
            MmkvUtils.put(Constants.KEY_IS_TOURIST,true)
            UserInfoManager.isTourist=true

            finish()
        }

    }

    private fun shakeAgreementLayout(layoutProtocol: LinearLayout) {

        val objectAnimator = ObjectAnimator.ofFloat(
            layoutProtocol, "translationX", 0f, -20f, 20f, -20f, 20f, -20f, 20f, -20f, 20f, 0f
        )
        objectAnimator.repeatMode = ValueAnimator.REVERSE
        objectAnimator.duration = 1000

        objectAnimator.interpolator = LinearInterpolator()
        objectAnimator.start()
    }
}