package com.a4a.dataindexer.ui.game

import androidx.lifecycle.ViewModel
import com.a4a.dataindexer.data.Game
import com.a4a.dataindexer.data.MainRepository
import com.android.volley.Response

class GameViewModel(private val mainRepository: MainRepository)
    : ViewModel() {

    fun getGames() = mainRepository.getAllGames()

    fun getGame(id: Long) = mainRepository.getGame(id)

    fun insert(game: Game) = mainRepository.insertGame(game)

    fun update(game: Game) = mainRepository.updateGame(game)

    fun delete(game: Game) = mainRepository.deleteGame(game)

    fun getDisks() = mainRepository.getAllDisks()

    fun searchByName(name: String, response: Response.Listener<String>) = mainRepository.searchGames(name,response)

    fun getByName(name: String, response: Response.Listener<String>) = mainRepository.getGameAPI(name,response)

    fun getCover(id: Long, response: Response.Listener<String>) = mainRepository.getGameCoverAPI(id,response)
}