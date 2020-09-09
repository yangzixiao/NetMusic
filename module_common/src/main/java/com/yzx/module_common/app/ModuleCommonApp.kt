package com.yzx.module_common.app

import com.yzx.lib_base.app.MusicCommonApplication
import com.yzx.lib_base.di.networkModule
import com.yzx.module_common.di.commonModule
import org.koin.core.context.loadKoinModules

class ModuleCommonApp : MusicCommonApplication() {

    override fun loadModules() {
        loadKoinModules(listOf(networkModule, commonModule))
    }
}