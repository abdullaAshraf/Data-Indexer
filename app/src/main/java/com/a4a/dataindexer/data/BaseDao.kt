package com.a4a.dataindexer.data

import androidx.room.*

@Dao
interface BaseDao<in T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(t: T): Long

    @Delete
    fun delete(type : T)

    @Update
    fun update(type : T)
}