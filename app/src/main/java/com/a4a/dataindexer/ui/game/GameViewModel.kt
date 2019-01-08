package com.a4a.dataindexer.ui.game

import androidx.lifecycle.ViewModel
import com.a4a.dataindexer.data.Game
import com.a4a.dataindexer.data.MainRepository

class GameViewModel(private val mainRepository: MainRepository)
    : ViewModel() {

    fun getGames() = mainRepository.getAllGames()

    fun getGame(id: Long) = mainRepository.getGame(id)

    fun insert(game: Game) = mainRepository.insertGame(game)

    fun update(game: Game) = mainRepository.updateGame(game)

    fun delete(game: Game) = mainRepository.deleteGame(game)

    fun getDisks() = mainRepository.getAllDisks()
}