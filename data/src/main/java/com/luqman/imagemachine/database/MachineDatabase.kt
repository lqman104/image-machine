package com.luqman.imagemachine.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.luqman.imagemachine.database.dao.MachineDao
import com.luqman.imagemachine.database.entity.MachineEntity
import com.luqman.imagemachine.database.entity.PictureEntity

@Database(entities = [MachineEntity::class, PictureEntity::class], version = 1)
abstract class MachineDatabase: RoomDatabase() {
    abstract fun machineDao(): MachineDao
}