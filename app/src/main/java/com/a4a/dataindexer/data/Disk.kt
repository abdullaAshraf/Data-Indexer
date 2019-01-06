package com.a4a.dataindexer.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "disk_table")
data class Disk(@PrimaryKey(autoGenerate = true)
                @ColumnInfo(name = "disk_id")
                val id: Long = 0,
                @ColumnInfo(name = "disk_name")
                val name: String,
                @ColumnInfo(name = "disk_size")
                val size: Double,
                @ColumnInfo(name = "disk_desc")
                val rate: String) {
}