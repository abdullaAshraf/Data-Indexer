package com.a4a.dataindexer.data

import android.util.Log
import androidx.lifecycle.LiveData
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class MainRepository constructor(private val gameDao: GameDao , private val diskDao: DiskDao) {

    fun insertGame(game: Game){
        Completable.fromCallable { gameDao.insert(game) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
            }
    }

    fun updateGame(game: Game){
        Completable.fromCallable { gameDao.update(game) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
            }
    }

    fun deleteGame(game: Game){
        Completable.fromCallable { gameDao.delete(game) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
            }
    }

    fun getGame(id: Long): LiveData<Game>{
        return gameDao.getGame(id)
    }

    fun getGamesAtDisk(id: Long): LiveData<Double>{
        return diskDao.getGamesSizeOnDisk(id)
    }

    fun getAllGames(): LiveData<List<Game>>{
        return gameDao.getAllGames()
    }

    fun insertDisk(disk: Disk){
        Completable.fromCallable { diskDao.insert(disk) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
            }
    }

    fun updateDisk(disk: Disk){
        Completable.fromCallable { diskDao.update(disk) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
            }
    }

    fun deleteDisk(disk: Disk){
        Completable.fromCallable { diskDao.delete(disk) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
            }
    }

    fun getDisk(id: Long): LiveData<Disk>{
        return diskDao.getDisk(id)
    }

    fun getAllDisks(): LiveData<List<Disk>>{
        return diskDao.getAllDisks()
    }


    companion object {
        // Singleton instantiation you already know and love
        @Volatile private var instance: MainRepository? = null

        fun getInstance(gameDao: GameDao , diskDao: DiskDao) =
            instance ?: synchronized(this) {
                instance ?: MainRepository(gameDao , diskDao).also { instance = it }
            }
    }
}