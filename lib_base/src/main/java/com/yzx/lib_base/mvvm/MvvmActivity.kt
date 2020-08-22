package com.yzx.lib_base.mvvm

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.yzx.lib_base.activity.StatusCompatActivity
import java.lang.reflect.ParameterizedType

/**
 * @author yzx
 * @date 2020/3/25
 * Description
 */
open class MvvmActivity<VM : MvvmModel> : StatusCompatActivity() {

    protected lateinit var viewModel: VM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createViewModel()
    }

    private fun createViewModel() {
        val type = javaClass.genericSuperclass
        //如果是泛型
        if (type is ParameterizedType) {
            val tp = type.actualTypeArguments[0]
            val tClass = tp as? Class<VM> ?: MvvmModel::class.java
            viewModel = ViewModelProvider(this).get(tClass) as VM
            lifecycle.addObserver(viewModel)
        }
    }
}