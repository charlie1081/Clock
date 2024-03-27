package com.example.clock.mmkv

import com.google.gson.Gson
import timber.log.Timber

object TimePrefManager : MmkvManager() {
    private const val KEY_TIME_SORT = "time_sort"
    private const val KEY_LANGUAGE = "language"
    private const val KEY_TIME_ZONES = "time_zones"

    var timeSort: Boolean
        get() = getPreference(KEY_TIME_SORT, true) as Boolean
        set(value) = updatePreference(KEY_TIME_SORT, value)

    var language: String
        get() = getPreference(KEY_LANGUAGE, "en") as String
        set(value) = updatePreference(KEY_LANGUAGE, value)

    var timeZones: String
        get() = getPreference(KEY_TIME_ZONES, "") as String
        set(value) = updatePreference(KEY_TIME_ZONES, value)

    fun getTimezoneArray(): ArrayList<String> {
        return ArrayList(stringConvertToList(timeZones))
    }

    private fun stringConvertToList(arrayString: String): List<String> {
        return arrayString.replace("[", "").replace("]", "").split(",")
    }

}