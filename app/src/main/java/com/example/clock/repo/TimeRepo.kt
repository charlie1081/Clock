package com.example.clock.repo

import com.example.clock.helper.NetworkHelper
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

object TimeRepo {
    private val gson = Gson()

    private val _timeResponseFlow = MutableSharedFlow<TimeResponse>()
    val timeResponseFlow: SharedFlow<TimeResponse> = _timeResponseFlow

    val mutex = Mutex()

    suspend fun getAvailableTimeZonesFromApi(): MutableList<String>? {
        return withContext(Dispatchers.IO) {
            NetworkHelper.httpGet("https://timeapi.io/api/TimeZone/AvailableTimeZones")
                ?.let { res ->
                    gson.fromJson(res, mutableListOf<String>().javaClass)
                }
        }
    }

    suspend fun getTimezoneTime(timeZone: String) {
        withContext(Dispatchers.IO) {
            mutex.withLock {
                NetworkHelper.httpGet("https://timeapi.io/api/Time/current/zone?timeZone=$timeZone")?.let { res ->
                    _timeResponseFlow.emit(gson.fromJson(res, TimeResponse::class.java))
                }
            }
        }
    }

    data class TimeResponse(val time: String, val timeZone: String)
}