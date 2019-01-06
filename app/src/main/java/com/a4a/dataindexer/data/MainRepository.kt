package com.a4a.dataindexer.data

import android.os.Debug
import android.util.Log
import androidx.lifecycle.LiveData
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class MainRepository constructor(private val gameDao: GameDao) {
    fun insert(game: Game){
        Observable.fromCallable { gameDao.insert(game) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
                Log.d("DataBase","Inserted ${game.name} to DB..." )
            }
    }

    fun update(game: Game){
        Observable.fromCallable { gameDao.update(game) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
                Log.d("DataBase","Inserted ${game.name} to DB..." )
            }
    }

    fun delete(game: Game){
        Observable.fromCallable { gameDao.delete(game) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
                Log.d("DataBase","Inserted ${game.name} to DB..." )
            }
    }

    fun getGame(id: Long): LiveData<Game>{
        return gameDao.getGame(id)
    }

    fun getAllGames(): LiveData<List<Game>>{
        return gameDao.getAllGames()
    }

    companion object {
        // Singleton instantiation you already know and love
        @Volatile private var instance: MainRepository? = null

        fun getInstance(gameDao: GameDao) =
            instance ?: synchronized(this) {
                instance ?: MainRepository(gameDao).also { instance = it }
            }
    }
}