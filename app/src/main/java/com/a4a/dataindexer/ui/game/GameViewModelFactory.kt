package com.a4a.dataindexer.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.a4a.dataindexer.data.MainRepository

class GameViewModelFactory(private val mainRepository: MainRepository)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GameViewModel(mainRepository) as T
    }
}