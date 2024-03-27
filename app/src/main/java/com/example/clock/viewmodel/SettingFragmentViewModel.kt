package com.example.clock.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.clock.mmkv.TimePrefManager
import com.example.clock.model.CheckboxItem
import com.example.clock.repo.TimeRepo
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class SettingFragmentViewModel(app: Application) : AndroidViewModel(app) {

    private val _availableTimeZones = MutableSharedFlow<MutableList<String>>()
    val availableTimeZones: SharedFlow<MutableList<String>> = _availableTimeZones

    fun getAvailableTimeZonesFrom() {
        viewModelScope.launch {
            TimeRepo.getAvailableTimeZonesFromApi()?.let {
                _availableTimeZones.emit(it)
            }
        }
    }

    fun checkAlreadyAddInList(list: ArrayList<CheckboxItem>, timezone: String): Boolean {
        return list.find { it.timeZone == timezone }?.let {
            true
        } ?: run {
            false
        }
    }

    fun saveTimezones(checkboxList: ArrayList<CheckboxItem>) {
        val allTimezoneName = arrayListOf<String>()
        checkboxList.forEach {
            allTimezoneName.add(it.timeZone)
        }
        TimePrefManager.timeZones = allTimezoneName.toString().replace(" ", "")
        Timber.d("timeZones ${TimePrefManager.timeZones}")
    }

    fun getAvailableTimeZonesFromPref(): ArrayList<CheckboxItem> {
        Timber.d("TimePrefManager.timeZones ${TimePrefManager.timeZones}")
        val timezoneList = TimePrefManager.getTimezoneArray()
        val result = arrayListOf<CheckboxItem>()
        timezoneList.forEach {
            result.add(CheckboxItem(it))
        }
        return result
    }
}