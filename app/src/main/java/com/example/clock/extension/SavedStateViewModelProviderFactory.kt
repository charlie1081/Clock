package com.example.clock.extension

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner

class SavedStateViewModelProviderFactory<T>(
    owner: SavedStateRegistryOwner,
    private val creator: (handle: SavedStateHandle) -> T,
    defaultArgs: Bundle? = null
): AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        val vm = creator(handle)
        return vm as T
    }
}