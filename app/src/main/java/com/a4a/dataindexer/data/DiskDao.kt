package com.a4a.dataindexer.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface DiskDao: BaseDao<Disk>{

    @Query(value = "Select * from disk_table")
    fun getAllDisks():LiveData<List<Disk>>

    @Query(value = "Select * from disk_table where disk_id = :diskId")
    fun getDisk(diskId:Long):LiveData<Disk>

    @Query(value = "Select sum(game_size) from game_table where game_disk = :diskId")
    fun getGamesSizeOnDisk(diskId:Long):LiveData<Double>
}