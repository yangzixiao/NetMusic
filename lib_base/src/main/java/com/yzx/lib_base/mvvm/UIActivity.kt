package com.yzx.lib_base.mvvm

import android.os.Bundle
import androidx.lifecycle.Observer
import com.yzx.lib_base.activity.StatusCompatActivity
import com.yzx.lib_base.base.BaseViewModel
import com.yzx.lib_base.ext.e
import com.yzx.lib_base.ext.toast
import com.yzx.lib_base.widget.dialog.LoadingDialog

/**
 * @author yzx
 * @date 2020/3/25
 * Description
 */
open class UIActivity : StatusCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private val loadingDialog: LoadingDialog by lazy {
        LoadingDialog()
    }

    fun showLoadingDialog() {
        if (!isDialogShowing()) {
            loadingDialog.show(supportFragmentManager, "loadingDialog")
        }
    }

    fun hideLoadingDialog() {
        if (isDialogShowing()) {
            loadingDialog.dismiss()
        }
    }

    private fun isDialogShowing(): Boolean {
        val dialog = loadingDialog.dialog ?: return false
        return dialog.isShowing
    }


    fun initViewModel(baseViewModel: BaseViewModel) {
        baseViewModel.loadingState.observe(this, Observer {
            e("baseViewModel.loadingState")
            if (it)
                showLoadingDialog()
            else hideLoadingDialog()
        })
        baseViewModel.toastStringMsg.observe(this, Observer {
            if (it.isNullOrEmpty()){
                return@Observer
            }
            toast(it)
        })
    }
}