package com.yzx.module_common.di

import com.yzx.module_common.playlistdetail.PlayListDetailRepository
import com.yzx.module_common.playlistdetail.PlayListDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val commonModule = module {

    single {
        ModuleCommonServiceProvider(get())
    }
    single {
        PlayListDetailRepository(get())
    }
    viewModel {
        PlayListDetailViewModel(get())
    }
}