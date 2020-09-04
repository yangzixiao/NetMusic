package com.yzx.lib_base.manager

import android.app.Application

object AppManager {

    lateinit var application: Application

    fun init(application: Application) {
        this.application = application
    }
}