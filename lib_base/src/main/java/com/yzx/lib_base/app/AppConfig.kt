package com.yzx.lib_base.app

/**
 * @author yzx
 * @date 2020/5/13
 * Description
 */
object AppConfig {

    /**
     * 登录
     */
    private const val MODULE_LOGIN = "com.yzx.module_login.LoginApp"
    private const val MODULE_BASE = "com.yzx.lib_base.BaseModuleApp"
    private const val MODULE_COMMON = "com.yzx.module_common.app.CommonModuleApp"


    val moduleApps = arrayOf(MODULE_LOGIN, MODULE_BASE, MODULE_COMMON)
}