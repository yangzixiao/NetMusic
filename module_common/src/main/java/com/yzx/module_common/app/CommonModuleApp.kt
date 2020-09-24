package com.yzx.module_common.app

import android.app.Application
import com.yzx.lib_base.app.BaseApplication
import com.yzx.lib_play_client.PlayerManager

class CommonModuleApp:BaseApplication() {
    override fun initModuleApp(application: Application?) {
        PlayerManager.getInstance().init(application)
    }

    override fun initModuleData(application: Application?) {

    }
}