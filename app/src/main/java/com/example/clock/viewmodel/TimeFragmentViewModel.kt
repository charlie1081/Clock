package com.example.clock.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clock.R
import com.example.clock.mmkv.TimePrefManager
import com.example.clock.model.Time
import com.example.clock.repo.TimeRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Locale

class TimeFragmentViewModel(app: Application): AndroidViewModel(app) {

    private val context = getApplication<Application>().applicationContext

    val timeResponseFlow = TimeRepo.timeResponseFlow

    fun setLocale(locale: Locale) {

    }

    fun getSortType(): Boolean {
        return TimePrefManager.timeSort
    }

    fun getAllTimezoneTime() {
        viewModelScope.launch {
            TimePrefManager.getTimezoneArray().forEach {
                TimeRepo.getTimezoneTime(it)
            }
        }
    }

    fun convertToTimeData(timeRes: TimeRepo.TimeResponse): Time {
        return Time(timeRes.time, timeRes.timeZone, timeRes.timeZone.substring(timeRes.timeZone.indexOf("/") + 1))
    }

    fun setSortType(type: String) {
        when (type) {
            context.resources.getString(R.string.asc_sort) -> {
                TimePrefManager.timeSort = true
            }

            else -> {
                TimePrefManager.timeSort = false
            }
        }
    }
}