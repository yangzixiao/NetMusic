package com.yzx.lib_base.utils;

import android.content.Context;

/**
 * @author yzx
 * @date 2019/4/3
 * Description
 */
public class StatusUtils {
    public static int getStateBarHeight(Context context){
        Context applicationContext = context.getApplicationContext();
        int result = DenistyUtils.dip2px(applicationContext,25);
        int resourceId = applicationContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = applicationContext.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
