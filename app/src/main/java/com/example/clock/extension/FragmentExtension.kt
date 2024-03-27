package com.example.clock.extension

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import androidx.savedstate.SavedStateRegistryOwner

inline fun <reified T: ViewModel> Fragment.getNavGraphViewModel(@IdRes navGraphId: Int, noinline creator: ((handle: SavedStateHandle) -> T)? = null): T {
    val backStackEntry = findNavController().getBackStackEntry(navGraphId)
    return createViewModelProvider(backStackEntry, backStackEntry, creator)
}

inline fun <reified T: ViewModel> createViewModelProvider(viewModelStoreOwner: ViewModelStoreOwner,
                                                          savedStateRegistryOwner: SavedStateRegistryOwner,
                                                          noinline creator: ((handle: SavedStateHandle) -> T)? = null): T {
    return when(creator) {
        null -> ViewModelProvider(viewModelStoreOwner)[T::class.java]
        else -> ViewModelProvider(viewModelStoreOwner,
            SavedStateViewModelProviderFactory(savedStateRegistryOwner, creator)
        )[T::class.java]
    }
}
