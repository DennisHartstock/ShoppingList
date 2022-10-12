package com.example.shoppingList.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    private val viewModelProviders: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Bug: null cannot be cast to non-null type T of com.example.shoppingList.presentation.ViewModelFactory.create
        return viewModelProviders[modelClass]?.get() as T
    }
}