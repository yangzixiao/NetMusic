package com.yzx.lib_base.app

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.bytedance.boost_multidex.BoostMultiDex
import com.yzx.lib_base.BuildConfig
import com.yzx.lib_base.Constant
import com.yzx.lib_base.http.RetrofitHelper
import com.yzx.lib_core.manager.AppManager
import com.yzx.lib_base.manager.UserInfoManager
import com.yzx.lib_base.model.UserDataBean
import com.yzx.lib_core.mmkv.MmkvUtils
import com.yzx.lib_core.utils.LogUtils
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * @author yzx
 * @date 2020/3/30
 * Description
 */
 abstract class MusicCommonApplication : BaseApplication() {
    companion object {
        const val TAG = "MusicCommonApplication"
    }

    override fun initModuleApp(application: Application?) {

        AppConfig.moduleApps.forEach {
            try {
                val moduleName = Class.forName(it)
                val baseApplication = moduleName.newInstance() as BaseApplication
                baseApplication.initModuleApp(application)
            } catch (e: Exception) {
                e.printStackTrace()
                LogUtils.e(TAG, "initModuleApp$e")
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
                LogUtils.e(TAG, "initModuleData$e")
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
            // use AndroidLogger as Koin Logger - default Level.INFO
            androidLogger()

            // use the Android context given there
            androidContext(this@MusicCommonApplication)

            // load properties from assets/koin.properties file
            androidFileProperties()

        }

        loadModules()

        val startTime = System.currentTimeMillis()
        LogUtils.e(TAG, "onCreateStartTime$startTime")
        MmkvUtils.init(this)
        ARouter.init(this)

        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        RetrofitHelper.getInstance().init(Constant.BASE_URL)
        val endTime = System.currentTimeMillis()
        LogUtils.e(TAG, "onCreateEndTime$endTime--TotalTime${endTime - startTime}")
        initModuleApp(this)
        initModuleData(this)

        //单独调试某个模块时，设置空的用户信息
        UserInfoManager.userInfoLiveData.value = UserDataBean()
    }

    abstract fun loadModules()
}