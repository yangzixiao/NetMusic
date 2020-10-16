package com.yzx.lib_base.mvvm

import android.app.Dialog
import androidx.lifecycle.Observer
import com.yzx.lib_base.R
import com.yzx.lib_base.base.BaseViewModel
import com.yzx.lib_base.base.LazyFragment
import com.yzx.lib_core.ext.toast


/**
 * @author yzx
 * @date 2020/7/18
 * Description
 */
open class UIFragment : LazyFragment() {
    private var baseViewModel: BaseViewModel? = null
    private  var loadingDialog: Dialog?=null

    fun showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = Dialog(requireContext())
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
        if (loadingDialog!=null&&loadingDialog!!.isShowing) {
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
            toast(it)
        })
    }
}