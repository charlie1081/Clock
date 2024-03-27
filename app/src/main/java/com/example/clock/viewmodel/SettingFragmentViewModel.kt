package com.example.clock.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.clock.model.CheckboxItem
import com.example.clock.repo.TimeRepo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

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
}