package com.a4a.dataindexer.data

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.ByteArrayInputStream
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "game_table")
data class Game(@PrimaryKey(autoGenerate = true)
                @ColumnInfo(name = "game_id")
                val id: Long = 0,
                @ColumnInfo(name = "game_name")
                val name: String,
                @ColumnInfo(name = "game_desc")
                val desc: String,
                @ColumnInfo(name = "game_size")
                val size: Double,
                @ColumnInfo(name = "game_rate")
                val rate: Double,
                @ColumnInfo(name = "game_genre")
                val genre: ArrayList<String>,
                @ColumnInfo(name = "game_date")
                val date: Date,
                //@ForeignKey()
                @ColumnInfo(name = "game_disk")
                val disk: Long,
                @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
                val cover: ByteArray) {

    override fun toString(): String {
        return "$name - $genre ($size GB)"
    }

    fun getSizeAsString(): String{
        val df = DecimalFormat("#.###")
        return when {
            size < 1 -> df.format(size*1024) + " MB"
            size > 500 -> df.format(size/1024) + " TB"
            else -> df.format(size) + " GB"
        }
    }

    fun getDateAsString():String{
        val form = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        return form.format(date)
    }
    fun getGenreAsString():String{
        val builder = StringBuilder()
        for ((pos,value) in genre.withIndex())
            builder.append(value).append(if(pos == genre.size -1) "" else ", ")
        return builder.toString()
    }

    fun byteArrayToImage(): Drawable {
        val stream = ByteArrayInputStream(cover)
        val bitmap = BitmapFactory.decodeStream(stream)
        return BitmapDrawable(bitmap)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Game

        if (id != other.id) return false
        if (name != other.name) return false
        if (size != other.size) return false
        if (rate != other.rate) return false
        if (genre != other.genre) return false
        if (date != other.date) return false
        if (!Arrays.equals(cover, other.cover)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + size.hashCode()
        result = 31 * result + rate.hashCode()
        result = 31 * result + genre.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + Arrays.hashCode(cover)
        return result
    }
}