package com.yzx.module_login.di

import com.yzx.module_login.provider.LoginApiProvider
import com.yzx.module_login.repository.LoginRepository
import com.yzx.module_login.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    single { LoginApiProvider(get()) }
    single { LoginRepository(get()) }

    viewModel {
        LoginViewModel(get())
    }
}