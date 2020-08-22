package com.yzx.lib_base.http

/**
 * @author yzx
 * @date 2020/5/13
 * Description
 */
object HttpCodeUtils {

    fun isRight(code: Int): Boolean {
        return code == 200
    }
}