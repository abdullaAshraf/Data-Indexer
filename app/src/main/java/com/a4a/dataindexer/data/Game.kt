package com.a4a.dataindexer.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_table")
data class Game(@PrimaryKey(autoGenerate = true)
                @ColumnInfo(name = "game_id")
                val id: Long = 0,
                @ColumnInfo(name = "game_name")
                val name: String,
                @ColumnInfo(name = "game_size")
                val size: Double,
                @ColumnInfo(name = "game_rate")
                val rate: Double,
                @ColumnInfo(name = "game_genre")
                val genre: String) {

    override fun toString(): String {
        return "$name - $genre ($size GB)"
    }

    fun getSizeAsString() = "$size GB"
}