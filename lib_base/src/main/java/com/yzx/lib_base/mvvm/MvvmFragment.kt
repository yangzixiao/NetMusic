package com.yzx.lib_base.mvvm

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

/**
 * @author yzx
 * @date 2020/7/18
 * Description
 */
open class MvvmFragment<VM : MvvmModel>:Fragment() {

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