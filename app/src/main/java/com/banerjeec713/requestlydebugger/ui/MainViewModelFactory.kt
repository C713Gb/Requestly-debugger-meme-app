package com.banerjeec713.requestlydebugger.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.banerjeec713.requestlydebugger.data.DataManager

class MainViewModelFactory internal constructor(private val dataManager: DataManager) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dataManager) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}