package com.yzx.lib_base.utils;

import android.util.Log;

import static com.yzx.lib_base.BuildConfig.DEBUG;

/**
 *
 * @author yzx
 * @date 2018/8/21
 * Description Log工具类
 */
public class LogUtils {

    /**
     * 加个前缀
     */
    public static final String ExtraTag = "ygl";

    public static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(ExtraTag + tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (DEBUG) {
            Log.i(ExtraTag + tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            Log.e(ExtraTag + tag, msg);
        }
    }
}
