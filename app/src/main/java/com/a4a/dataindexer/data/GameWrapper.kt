package com.a4a.dataindexer.data

import com.google.gson.annotations.SerializedName
import java.util.*

class GameWrapper(val game: GameIn)

class GameIn(
    @SerializedName("name")
    val name: String,
    @SerializedName("summary")
    val desc: String,
    @SerializedName("total_rating")
    val rate: Double,
    @SerializedName("genres")
    val genre: ArrayList<Integer>,
    @SerializedName("first_release_date")
    val date: Long,
    @SerializedName("cover")
    val cover: Long
)