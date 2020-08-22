package com.yzx.lib_base.app

import android.app.Application

/**
 * @author yzx
 * @date 2020/5/13
 * Description
 */
 abstract class BaseApplication :Application(){

    /**
     * Application 初始化
     */
    abstract fun initModuleApp(application: Application?)

    /**
     * 所有 Application 初始化后的自定义操作
     */
    abstract fun initModuleData(application: Application?)
}