package com.yzx.lib_core.mmkv

import android.os.Parcelable
import com.tencent.mmkv.MMKV

/**
 * @author yzx
 * @date 2020/5/13
 * Description Mmkv
 */
object MmkvUtils {

    fun put(key: String, value: String) {
        MMKV.defaultMMKV().encode(key, value)
    }

    fun put(key: String, value: Boolean) {
        MMKV.defaultMMKV().encode(key, value)
    }

    fun put(key: String, value: Int) {
        MMKV.defaultMMKV().encode(key, value)
    }

    fun put(key: String, value: Double) {
        MMKV.defaultMMKV().encode(key, value)
    }

    fun put(key: String, value: Float) {
        MMKV.defaultMMKV().encode(key, value)
    }

    fun put(key: String, value: Long) {
        MMKV.defaultMMKV().encode(key, value)
    }

    fun put(key: String, value: Parcelable) {
        MMKV.defaultMMKV().encode(key, value)
    }

    fun get(key: String, default: String?): String {
        return MMKV.defaultMMKV().decodeString(key, default)
    }

    fun get(key: String, default: Boolean): Boolean {
        return MMKV.defaultMMKV().decodeBool(key, default)
    }

    fun get(key: String, default: Int): Int {
        return MMKV.defaultMMKV().decodeInt(key, default)
    }

    fun get(key: String, default: Double): Double {
        return MMKV.defaultMMKV().decodeDouble(key, default)
    }

    fun get(key: String, default: Float): Float {
        return MMKV.defaultMMKV().decodeFloat(key, default)
    }

    fun get(key: String, default: Long): Long {
        return MMKV.defaultMMKV().decodeLong(key, default)
    }

    fun get(key: String, clz: Class<Parcelable>): Parcelable {
        return MMKV.defaultMMKV().decodeParcelable(key, clz)
    }
}