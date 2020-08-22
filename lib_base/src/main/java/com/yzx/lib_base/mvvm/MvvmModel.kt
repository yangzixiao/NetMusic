package com.yzx.lib_base.mvvm

import android.util.Log
import androidx.lifecycle.*
import com.yzx.lib_base.http.RetrofitHelper
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import retrofit2.Retrofit

/**
 * @author yzx
 * @date 2020/3/25
 * Description
 */
open class MvvmModel : ViewModel(), LifecycleObserver {

    companion object {
        const val TAG = "MvvmModel"
    }

    protected val retrofit: Retrofit by lazy {
        RetrofitHelper.getInstance().retrofit
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(){
        if (viewModelScope.isActive){
            viewModelScope.cancel()
            Log.e(TAG, this.javaClass.simpleName+"onDestroy")
        }

    }
}