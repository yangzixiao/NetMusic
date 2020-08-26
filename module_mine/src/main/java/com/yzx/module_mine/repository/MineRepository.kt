package com.yzx.module_mine.repository

import com.yzx.module_mine.api.MineApi
import com.yzx.module_mine.provider.MineApiProvider

class MineRepository(private val mineApiProvider: MineApiProvider) {

    fun getApi():MineApi{
        return mineApiProvider.getMineApi()
    }
}