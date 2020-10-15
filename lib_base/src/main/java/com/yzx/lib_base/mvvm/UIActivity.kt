package com.yzx.lib_base.mvvm


import android.app.Dialog
import android.view.View
import androidx.annotation.MenuRes
import androidx.lifecycle.Observer
import com.yzx.lib_base.R
import com.yzx.lib_base.activity.StatusCompatActivity
import com.yzx.lib_base.base.BaseViewModel
import com.yzx.lib_base.ext.getDefaultStatusHeight

/**
 * @author yzx
 * @date 2020/3/25
 * Description
 */
open class UIActivity : StatusCompatActivity() {

    private var baseViewModel: BaseViewModel? = null

    fun initStatus(statusView: View) {
        val layoutParams = statusView.layoutParams
        layoutParams.height = getDefaultStatusHeight()
        statusView.layoutParams = layoutParams
    }

    fun initToolbar(toolbar: androidx.appcompat.widget.Toolbar, @MenuRes menu: Int = 0) {
        if (menu != 0) {
            toolbar.inflateMenu(menu)
        }
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private var loadingDialog: Dialog? = null

    fun showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = Dialog(this)
            loadingDialog!!.setCanceledOnTouchOutside(false)
            loadingDialog!!.setContentView(R.layout.layout_loaidng_dialog)
            loadingDialog!!.setOnCancelListener {
                baseViewModel?.cancelRequest()
            }
        }

        if (!loadingDialog!!.isShowing) {
            loadingDialog!!.show()
        }
    }


    fun hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog!!.isShowing) {
            loadingDialog!!.dismiss()
        }
    }

    fun initViewModel(baseViewModel: BaseViewModel) {
        this.baseViewModel = baseViewModel
        baseViewModel.loadingState.observe(this, {
            if (it) showLoadingDialog()
            else hideLoadingDialog()
        })
        baseViewModel.toastStringMsg.observe(this, Observer {
            if (it.isNullOrEmpty()) {
                return@Observer
            }
        })
    }
}