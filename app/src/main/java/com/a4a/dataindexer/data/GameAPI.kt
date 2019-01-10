package com.a4a.dataindexer.data

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*


data class GameAPI(
    @SerializedName("id")
    val id: Long = 0,
    @SerializedName("name")
    val name: String,
    @SerializedName("summary")
    val desc: String,
    @SerializedName("total_rating")
    val rate: Double,
    @SerializedName("genres")
    var genre: ArrayList<Int>,
    @SerializedName("first_release_date")
    val date: Long,
    @SerializedName("cover")
    val cover: Long
) {

    fun getDateAsString(): String {
        val ts = Timestamp(date*1000)
        val form = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        return form.format(Date(ts.time))
    }

    fun getGenreAsString(): String {
        val builder = StringBuilder()
        if(genre == null)
            genre =  ArrayList(0)
        for ((pos, value) in genre.withIndex()) {
            builder.append(map[value]).append(if (pos == genre.size - 1) "" else ", ")
        }
        return builder.toString()
    }

    companion object {
        val map: HashMap<Int, String> = hashMapOf(
            2 to "Point-and-click",
            4 to "Fighting",
            5 to "Shooter",
            7 to "Music",
            8 to "Platform",
            9 to "Puzzle",
            10 to "Racing",
            11 to "Real Time Strategy (RTS)",
            12 to "Role-playing (RPG)",
            13 to "Simulator",
            14 to "Sport",
            15 to "Strategy",
            16 to "Turn-based strategy (TBS)",
            24 to "Tactical",
            25 to "Hack and slash/Beat 'em up",
            26 to "Quiz/Trivia",
            30 to "Pinball",
            31 to "Adventure",
            32 to "Indie",
            33 to "Arcade"
        )
    }
}