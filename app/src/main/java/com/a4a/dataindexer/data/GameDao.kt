package com.a4a.dataindexer.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface GameDao: BaseDao<Game>{

    @Query(value = "Select * from game_table")
    fun getAllGames():LiveData<List<Game>>

    @Query(value = "Select * from game_table where game_id = :gameId")
    fun getGame(gameId:Long):LiveData<Game>
}