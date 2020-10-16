package com.yzx.lib_core.manager

import android.app.Application

object AppManager {

    lateinit var application: Application

    fun init(application: Application) {
        AppManager.application = application
    }
}