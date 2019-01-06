package com.a4a.dataindexer.utilities

import android.app.Application
import android.content.Context
import com.a4a.dataindexer.data.DataIndexerDatabase
import com.a4a.dataindexer.data.MainRepository
import com.a4a.dataindexer.ui.game.GameViewModelFactory

object InjectorUtils {

    fun provideGameViewModelFactory(context: Context): GameViewModelFactory {
        // ViewModelFactory needs a repository, which in turn needs a DAO from a database
        // The whole dependency tree is constructed right here, in one place
        val mainRepository = MainRepository.getInstance(DataIndexerDatabase.getInstance(context).gameDao())
        return GameViewModelFactory(mainRepository)
    }
}