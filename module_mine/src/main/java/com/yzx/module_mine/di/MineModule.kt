package com.yzx.module_mine.di

import com.yzx.module_mine.provider.MineApiProvider
import com.yzx.module_mine.repository.MineRepository
import com.yzx.module_mine.viewmodel.MineViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mineModule = module {
    single { MineApiProvider(get()) }
    single { MineRepository(get()) }

    viewModel {
        MineViewModel(get())
    }
}