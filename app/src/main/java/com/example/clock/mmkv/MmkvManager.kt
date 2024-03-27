package com.example.clock.mmkv

import android.os.Parcelable
import com.tencent.mmkv.MMKV

abstract class MmkvManager {
    val mmkv: MMKV = MMKV.mmkvWithID("main", MMKV.MULTI_PROCESS_MODE)

    open fun getPreference(key: String, value: Any?): Any? {
        return when (value) {
            is Int -> mmkv.decodeInt(key, value)
            is Long -> mmkv.decodeLong(key, value)
            is String -> mmkv.decodeString(key, value)
            is Float -> mmkv.decodeFloat(key, value)
            is Double -> mmkv.decodeDouble(key, value)
            is Boolean -> mmkv.decodeBool(key, value)
            is Class<*>? -> mmkv.decodeParcelable(key, value as? Class<Parcelable>)
            else -> value
        }
    }

    open fun updatePreference(key: String, value: Any?) {
        when (value) {
            is Int -> mmkv.encode(key, value)
            is Long -> mmkv.encode(key, value)
            is String -> mmkv.encode(key, value)
            is Float -> mmkv.encode(key, value)
            is Double -> mmkv.encode(key, value)
            is Boolean -> mmkv.encode(key, value)
            is Parcelable -> mmkv.encode(key, value)
        }
    }

}