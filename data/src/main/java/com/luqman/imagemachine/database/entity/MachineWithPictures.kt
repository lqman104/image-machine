package com.luqman.imagemachine.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class MachineWithPictures(
    @Embedded
    val machineEntity: MachineEntity,
    @Relation(
        parentColumn = MachineEntity.ID,
        entityColumn = PictureEntity.MACHINE_ID
    )
    val pictures: List<PictureEntity>
)