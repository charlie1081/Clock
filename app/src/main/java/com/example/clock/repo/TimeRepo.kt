package com.example.clock.repo

import com.example.clock.helper.NetworkHelper
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object TimeRepo {
    private val gson = Gson()
    suspend fun getAvailableTimeZonesFromApi(): MutableList<String>? {
        return withContext(Dispatchers.IO) {
            NetworkHelper.httpGet("https://timeapi.io/api/TimeZone/AvailableTimeZones")
                ?.let { res ->
                    gson.fromJson(res, mutableListOf<String>().javaClass)
                }
        }
    }
}