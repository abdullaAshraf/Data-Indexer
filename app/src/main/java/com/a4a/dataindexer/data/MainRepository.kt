package com.a4a.dataindexer.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers


class MainRepository constructor(
    private val gameDao: GameDao,
    private val diskDao: DiskDao,
    private val volleyQueue: RequestQueue
) {

    fun insertGame(game: Game) {
        Completable.fromCallable { gameDao.insert(game) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
            }
    }

    fun updateGame(game: Game) {
        Completable.fromCallable { gameDao.update(game) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
            }
    }

    fun deleteGame(game: Game) {
        Completable.fromCallable { gameDao.delete(game) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
            }
    }

    fun getGame(id: Long): LiveData<Game> {
        return gameDao.getGame(id)
    }

    fun getGamesAtDisk(id: Long): LiveData<Double> {
        return diskDao.getGamesSizeOnDisk(id)
    }

    fun getAllGames(): LiveData<List<Game>> {
        return gameDao.getAllGames()
    }

    fun insertDisk(disk: Disk) {
        Completable.fromCallable { diskDao.insert(disk) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
            }
    }

    fun updateDisk(disk: Disk) {
        Completable.fromCallable { diskDao.update(disk) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
            }
    }

    fun deleteDisk(disk: Disk) {
        Completable.fromCallable { diskDao.delete(disk) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
            }
    }

    fun getDisk(id: Long): LiveData<Disk> {
        return diskDao.getDisk(id)
    }

    fun getAllDisks(): LiveData<List<Disk>> {
        return diskDao.getAllDisks()
    }

    fun searchGames(name: String, response: Response.Listener<String>) {
        val url = "https://api-v3.igdb.com/search/"
        val req = object: StringRequest(
            Request.Method.POST, url,
            response,
            Response.ErrorListener {Log.d("MyTag", "Error") }) {

            @Throws(AuthFailureError::class)
            override fun getHeaders(): MutableMap<String, String> {
                val headers = mutableMapOf<String, String>()
                headers["user-key"] = "45bd6d2247de62de48d37f4be0b8c0e1"
                return headers
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                //val str = "fields game.name , game.summary , game.cover , game.first_release_date , game.total_rating , game.genres; search \"$name\"; where game != null;"
                val str = "fields game.name; search \"$name\"; where game != null; limit 5;"
                return str.toByteArray()
            }
        }
        volleyQueue.add(req)
    }

    fun getGameAPI(name: String, response: Response.Listener<String>) {
        val url = "https://api-v3.igdb.com/games/"
        val req = object: StringRequest(
            Request.Method.POST, url,
            response,
            Response.ErrorListener {Log.d("MyTag", "Error") }) {

            @Throws(AuthFailureError::class)
            override fun getHeaders(): MutableMap<String, String> {
                val headers = mutableMapOf<String, String>()
                headers["user-key"] = "45bd6d2247de62de48d37f4be0b8c0e1"
                return headers
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                val str = "fields name , summary , cover , first_release_date , total_rating , genres; where name = \"$name\";"
                return str.toByteArray()
            }
        }
        volleyQueue.add(req)
    }

    fun getGameCoverAPI(id: Long, response: Response.Listener<String>) {
        val url = "https://api-v3.igdb.com/covers"
        val req = object: StringRequest(
            Request.Method.POST, url,
            response,
            Response.ErrorListener {Log.d("MyTag", "Error") }) {

            @Throws(AuthFailureError::class)
            override fun getHeaders(): MutableMap<String, String> {
                val headers = mutableMapOf<String, String>()
                headers["user-key"] = "45bd6d2247de62de48d37f4be0b8c0e1"
                return headers
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                val str = "fields url ; where id = $id;"
                return str.toByteArray()
            }
        }
        volleyQueue.add(req)
    }


    companion object {
        // Singleton instantiation you already know and love
        @Volatile
        private var instance: MainRepository? = null

        fun getInstance(gameDao: GameDao, diskDao: DiskDao , volleyQueue: RequestQueue) =
            instance ?: synchronized(this) {
                instance ?: MainRepository(gameDao, diskDao, volleyQueue ).also { instance = it }
            }
    }
}