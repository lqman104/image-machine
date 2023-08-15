package com.luqman.imagemachine.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "machine_entity")
data class MachineEntity(
    @PrimaryKey
    @ColumnInfo(name = ID)
    val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = NAME)
    val name: String,
    @ColumnInfo(name = TYPE)
    val type: String,
    @ColumnInfo(name = CODE)
    val code: String,
    @ColumnInfo(name = LAST_MAINTAIN)
    val lastMaintain: Long,
) {
    companion object {
        const val ID = "id"
        const val NAME = "name"
        const val TYPE = "type"
        const val CODE = "code"
        const val LAST_MAINTAIN = "last_maintain"
    }
}