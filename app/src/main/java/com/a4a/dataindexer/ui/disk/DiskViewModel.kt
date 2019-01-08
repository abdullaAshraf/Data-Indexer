package com.a4a.dataindexer.ui.disk

import androidx.lifecycle.ViewModel
import com.a4a.dataindexer.data.Disk
import com.a4a.dataindexer.data.Game
import com.a4a.dataindexer.data.MainRepository

class DiskViewModel(private val mainRepository: MainRepository)
    : ViewModel() {

    fun getDisk(id: Long) = mainRepository.getGame(id)

    fun insert(disk: Disk) = mainRepository.insertDisk(disk)

    fun update(disk: Disk) = mainRepository.updateDisk(disk)

    fun delete(disk: Disk) = mainRepository.deleteDisk(disk)

    fun getDisks() = mainRepository.getAllDisks()

    fun getDiskSize(id: Long) = mainRepository.getGamesAtDisk(id)
}