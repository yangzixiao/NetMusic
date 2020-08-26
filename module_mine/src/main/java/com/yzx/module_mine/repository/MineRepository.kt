package com.yzx.module_mine.repository

import com.yzx.module_mine.api.MineApi
import com.yzx.module_mine.model.MinePagerData
import com.yzx.module_mine.provider.MineApiProvider

class MineRepository(private val mineApiProvider: MineApiProvider) {

    fun getApi(): MineApi {
        return mineApiProvider.getMineApi()
    }

    fun getMinePagerData(uid: String? = null): MinePagerData {
        val minePagerData = MinePagerData()
        if (uid.isNullOrEmpty()) {

        } else {

        }
        return minePagerData
    }
}