package com.example.clock

import android.app.Application
import com.tencent.mmkv.MMKV
import timber.log.Timber

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        MMKV.initialize(this)
    }
}