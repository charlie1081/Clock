package com.example.clock.mmkv

import com.tencent.mmkv.MMKV

object TimePrefManager: MmkvManager() {
    private const val KEY_TIME_SORT = "time_sort"
    private const val KEY_LANGUAGE = "language"

    var timeSort: Boolean
        get() = mmkv.getBoolean(KEY_TIME_SORT,true)
        set(value) = updatePreference(KEY_TIME_SORT, value)

    var language: String
        get() = mmkv.getString(KEY_LANGUAGE, "en") ?: "en"
        set(value) = updatePreference(KEY_LANGUAGE, value)
}