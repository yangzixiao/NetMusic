package com.yzx.module_mine.app

import com.yzx.lib_base.app.MusicCommonApplication
import com.yzx.lib_base.di.networkModule
import com.yzx.module_mine.di.mineModule
import org.koin.core.context.loadKoinModules

class ModuleMineApp : MusicCommonApplication() {

    override fun loadModules() {
        loadKoinModules(listOf(networkModule, mineModule))
    }
}