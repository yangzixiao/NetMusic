package com.yzx.module_mine.model

/**
 * 我的音乐数据
 */
class MyMusicBean(
    var icon: Int,
    var title: String,
    var des: String? = null,
    var background: Any? = null,
    var keyColor: Int = 0x55000000
)