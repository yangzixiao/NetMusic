package com.yzx.lib_base

import android.widget.TextView
import com.yzx.lib_base.base.BaseActivity
import com.yzx.lib_base.base.BaseViewModel
import com.yzx.lib_base.mvvm.MvvmModel

/**
 * @author yzx
 * @date 2020/4/22
 * Description 底部有
 */
open class BaseBottomSongInfoActivity<VM : BaseViewModel> : BaseActivity<VM>() {

    private var tvSongName: TextView? = null
    private var tvSingerName: TextView? = null

    /**
     * 设置歌曲信息
     */
    fun setSongInfo(songName: String, singerName: String, posterUrl: String) {
        if (tvSongName == null) {
            tvSongName = findViewById(R.id.tvSongName)
        }
        tvSongName?.text = songName
        if (tvSingerName == null) {
            tvSingerName = findViewById(R.id.tvSingerName)
        }
        tvSingerName?.text = singerName
    }
}