package com.yzx.lib_base.activity;

import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.yzx.lib_base.widget.status.StatusBarCompat;


/**
 * @author yzx
 * @date 2019/7/9
 * Description 状态栏优化
 */
public  class StatusCompatActivity extends AnimCompatActivity {

    /**
     * 设置透明状态栏
     */
    protected void setTransparentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StatusBarCompat.translucentStatusBar(this, true);
        } else {
            StatusBarCompat.translucentStatusBar(this);
        }
    }

    protected void setStatusDarkFont() {
        StatusBarCompat.changeToLightStatusBar(this);
    }

    protected void setStatusWhiteFont() {
        StatusBarCompat.cancelLightStatusBar(this);
    }

    protected void setFullScreen() {
        Window window = getWindow();
        View decorView = window.getDecorView();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
        window.setAttributes(lp);
        int systemUiVisibility = decorView.getSystemUiVisibility();
        //隐藏导航栏//隐藏状态栏
        int flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        systemUiVisibility |= flags;
        decorView.setSystemUiVisibility(systemUiVisibility);
    }
}
