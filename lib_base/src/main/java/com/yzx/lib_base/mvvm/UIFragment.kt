package com.yzx.lib_base.mvvm

import com.yzx.lib_base.base.LazyFragment
import com.yzx.lib_base.widget.dialog.LoadingDialog

/**
 * @author yzx
 * @date 2020/7/18
 * Description
 */
open class UIFragment : LazyFragment() {

    private val loadingDialog: LoadingDialog by lazy {
        LoadingDialog()
    }

    fun showLoadingDialog() {
        if (!isDialogShowing()) {
            loadingDialog.show(childFragmentManager, "loadingDialog")
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
}