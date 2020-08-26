package com.yzx.module_mine

import android.os.Bundle
import com.yzx.lib_base.ARouter.ARouterNavUtils
import com.yzx.lib_base.ARouter.ARouterPath.FRAGMENT_MINE
import com.yzx.lib_base.R
import com.yzx.lib_base.base.BaseActivity
import com.yzx.module_mine.databinding.ActivityMineMainBinding

/**
 * @author yzx
 * @date 2020/5/15
 * Description 当Mine单独运行时的入口
 */
class MineMainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTransparentStatus()
        setStatusDarkFont()
        setContentView(ActivityMineMainBinding.inflate(layoutInflater).root)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, ARouterNavUtils.getFragment(FRAGMENT_MINE)).commit()
    }
}