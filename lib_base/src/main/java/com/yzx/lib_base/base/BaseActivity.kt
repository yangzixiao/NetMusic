package com.yzx.lib_base.base

import android.annotation.SuppressLint
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.yzx.lib_base.mvvm.UIActivity

/**
 * @author yzx
 * @date 2020/4/8
 * Description
 */
open class BaseActivity : UIActivity() {



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    /**
     * popMenu icon可见
     *
     * @param toolbar
     */
    fun setMenuIconVisible(toolbar: Toolbar) {
        val menu: Menu = toolbar.menu
        if (menu != null) {
            if (menu.javaClass.simpleName == "MenuBuilder") {
                try {
                    @SuppressLint("PrivateApi") val m = menu.javaClass.getDeclaredMethod(
                        "setOptionalIconsVisible", java.lang.Boolean.TYPE
                    )
                    m.isAccessible = true
                    m.invoke(menu, true)
                } catch (e: Exception) {
                }
            }
        }
    }

}