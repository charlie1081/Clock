package com.example.clock.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.clock.R
import com.example.clock.mmkv.TimePrefManager
import timber.log.Timber
import java.util.Locale

class TimeFragmentViewModel(app: Application): AndroidViewModel(app) {

    private val context = getApplication<Application>().applicationContext

    fun setLocale(locale: Locale) {

    }

    fun getSortType(): String {
        return if(TimePrefManager.timeSort) {
            context.resources.getString(R.string.asc_sort)
        } else {
            context.resources.getString(R.string.desc_sort)
        }
    }
}