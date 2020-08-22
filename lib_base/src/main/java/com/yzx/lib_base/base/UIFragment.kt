package com.yzx.lib_base.base

import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yzx.lib_base.mvvm.MvvmActivity
import com.yzx.lib_base.mvvm.MvvmFragment
import java.lang.reflect.ParameterizedType

/**
 * @author yzx
 * @date 2019/12/23
 * Description Activity基类
 */
open class UIFragment<VM : BaseViewModel> : MvvmFragment<VM>(),

    DialogInterface.OnCancelListener {


    /**
     * 获取EditText内容
     */
    fun getEditContent(editText: EditText): String? {
        return editText.text.toString().trim()
    }


    override fun onCancel(p0: DialogInterface?) {
        viewModel.cancelRequest()
    }

    private val loadingDialog: ProgressDialog by lazy {
        ProgressDialog(context)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLoadingAndMsg()
    }


    private fun initLoadingAndMsg() {

        loadingDialog.setMessage("请稍后")
        loadingDialog.setCanceledOnTouchOutside(false)
        loadingDialog.setOnCancelListener(this)

        viewModel.loadingState.observe(this, Observer {
            if (it) loadingDialog.show() else
                if (loadingDialog.isShowing) {
                    loadingDialog.hide()
                }
            onLoadingStateChanged(it)
        })

        viewModel.toastStringMsg.observe(this, Observer {
            toast(it)
        })

        viewModel.toastStringResourceMsg.observe(this, Observer {
            toast(it)
        })
    }

    /**
     * loading状态
     */
   open fun onLoadingStateChanged(loadingState: Boolean) {

    }

    fun toast(@StringRes msg:Int){
        if (msg==0){
            return
        }
        toast(getString(msg))

    }
    /**
     * toast
     */
    fun toast(msg: String?) {
        if (msg.isNullOrBlank()) {
            return
        }
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

}