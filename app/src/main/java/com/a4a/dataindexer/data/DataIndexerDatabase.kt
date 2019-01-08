package com.a4a.dataindexer.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.a4a.dataindexer.utilities.Converters

@Database(entities = [Game::class , Disk::class], version = 5, exportSchema = false)
@TypeConverters(Converters::class)
abstract  class DataIndexerDatabase: RoomDatabase() {
    abstract fun gameDao(): GameDao
    abstract fun diskDao(): DiskDao

    companion object {
        @Volatile private var instance: DataIndexerDatabase? = null

        fun getInstance(context: Context) =  instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(context.applicationContext, DataIndexerDatabase::class.java, "main_database").fallbackToDestructiveMigration().build().also { instance = it }
        }
    }
}
