package com.yzx.netmusic

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.bytedance.boost_multidex.BoostMultiDex
import com.tencent.mmkv.MMKV
import com.yzx.lib_base.BuildConfig
import com.yzx.lib_base.app.AppConfig
import com.yzx.lib_base.app.BaseApplication
import com.yzx.lib_base.app.MusicCommonApplication
import com.yzx.lib_base.di.networkModule
import com.yzx.lib_base.manager.AppManager
import com.yzx.lib_base.utils.LogUtils
import com.yzx.module_common.di.commonModule
import com.yzx.module_login.di.loginModule
import com.yzx.module_mine.di.mineModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class MusicApplication : BaseApplication() {

    override fun initModuleApp(application: Application?) {

        AppConfig.moduleApps.forEach {
            try {
                val moduleName = Class.forName(it)
                val baseApplication = moduleName.newInstance() as BaseApplication
                baseApplication.initModuleApp(application)
            } catch (e: Exception) {
                e.printStackTrace()
                LogUtils.e(MusicCommonApplication.TAG, "initModuleApp$e")
            }
        }
    }

    override fun initModuleData(application: Application?) {
        AppConfig.moduleApps.forEach {
            try {
                val moduleName = Class.forName(it)
                val baseApplication = moduleName.newInstance() as BaseApplication
                baseApplication.initModuleData(application)
            } catch (e: Exception) {
                e.printStackTrace()
                LogUtils.e(MusicCommonApplication.TAG, "initModuleData$e")
            }
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        /**
         * 字节跳动MultiDex方案
         * https://github.com/bytedance/BoostMultiDex
         */
        BoostMultiDex.install(base)
    }

    override fun onCreate() {
        super.onCreate()
        AppManager.init(this)
        startKoin {
            androidLogger()
            androidContext(this@MusicApplication)
            androidFileProperties()
        }
        loadKoinModules(listOf(networkModule, loginModule,mineModule,commonModule))

        MMKV.initialize(this)


        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)

        initModuleApp(this)
        initModuleData(this)
    }
}