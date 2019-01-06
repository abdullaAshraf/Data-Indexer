package com.a4a.dataindexer.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.a4a.dataindexer.utilities.Converters

@Database(entities = [Game::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract  class DataIndexerDatabase: RoomDatabase() {
    abstract fun gameDao(): GameDao

    companion object {
        @Volatile private var instance: DataIndexerDatabase? = null

        fun getInstance(context: Context) =  instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(context.applicationContext, DataIndexerDatabase::class.java, "main_database").fallbackToDestructiveMigration().build().also { instance = it }
        }
    }
}
