package com.luqman.imagemachine.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "picture_entity")
data class PictureEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = PATH)
    val path: String,
    @ColumnInfo(name = MACHINE_ID)
    val machineId: String
) {
    companion object {
        const val PATH = "path"
        const val MACHINE_ID = "machine_id"
    }
}